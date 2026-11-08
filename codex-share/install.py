#!/usr/bin/env python3
"""Install repository guidance once; never execute a model or project commands."""
from __future__ import annotations

import argparse
import hashlib
import json
import os
from pathlib import Path
import sys
import tempfile
from datetime import datetime, timezone

BEGIN = b"<!-- codex-share:begin -->"
END = b"<!-- codex-share:end -->"
BOM = b"\xef\xbb\xbf"
SKILLS = ("document-driven-code-workflow", "git-ui-playwright")


class InstallError(Exception):
    pass


def sha(data: bytes) -> str:
    return hashlib.sha256(data).hexdigest()


def safe_path(root: Path, path: Path) -> Path:
    try:
        relative = path.relative_to(root)
    except ValueError as exc:
        raise InstallError(f"Path is outside target project: {path}") from exc
    cursor = root
    for part in relative.parts:
        cursor = cursor / part
        if cursor.is_symlink() or getattr(cursor, "is_junction", lambda: False)():
            raise InstallError(f"Refusing redirected target path: {cursor}")
    if not path.resolve().is_relative_to(root):
        raise InstallError(f"Resolved path is outside project: {path}")
    return path


def read_optional(path: Path) -> bytes:
    if path.exists() and not path.is_file():
        raise InstallError(f"Expected file: {path}")
    return path.read_bytes() if path.exists() else b""


def instruction_target(project: Path) -> Path:
    override = safe_path(project, project / "AGENTS.override.md")
    if read_optional(override).removeprefix(BOM).strip():
        return override
    return safe_path(project, project / "AGENTS.md")


def merge_block(original: bytes, block: bytes) -> bytes:
    if original.count(BEGIN) != original.count(END) or original.count(BEGIN) > 1:
        raise InstallError("Malformed or duplicate codex-share markers; no files changed.")
    newline = b"\r\n" if b"\r\n" in original else b"\n"
    block = block.replace(b"\r\n", b"\n").strip().replace(b"\n", newline)
    if BEGIN in original:
        start, end = original.index(BEGIN), original.index(END) + len(END)
        if original.index(END) < start:
            raise InstallError("Reversed codex-share markers; no files changed.")
        return original[:start] + block + original[end:]
    prefix = BOM if original.startswith(BOM) else b""
    body = original.removeprefix(BOM)
    return prefix + block + newline + newline + body


def atomic_write(path: Path, data: bytes) -> None:
    path.parent.mkdir(parents=True, exist_ok=True)
    descriptor, temporary = tempfile.mkstemp(prefix=".codex-share-", dir=path.parent)
    temporary_path = Path(temporary)
    try:
        with os.fdopen(descriptor, "wb") as stream:
            stream.write(data)
        os.replace(temporary_path, path)
    finally:
        temporary_path.unlink(missing_ok=True)


def run(package: Path, project: Path, *, check: bool = False, dry_run: bool = False) -> dict:
    project = project.resolve()
    package = package.resolve()
    if not project.is_dir() or package != project / "codex-share":
        raise InstallError("Place codex-share directly in the target project root before installation.")
    safe_path(project, package)
    guide = safe_path(project, package / "WORKFLOW.md")
    if not guide.is_file():
        raise InstallError("Package is missing WORKFLOW.md")
    block_path = safe_path(project, package / "integration/AGENTS.block.md")
    block = block_path.read_bytes()
    if block.count(BEGIN) != 1 or block.count(END) != 1 or block.index(BEGIN) > block.index(END):
        raise InstallError("Invalid bundled instruction block")
    target = instruction_target(project)
    original = read_optional(target)
    if original:
        try:
            original.decode("utf-8-sig")
        except UnicodeDecodeError as exc:
            raise InstallError("Existing instruction file is not UTF-8; convert explicitly before installation.") from exc
    merged = merge_block(original, block)
    warnings = []
    if len(merged) > 32768:
        warnings.append("Instruction file exceeds Codex's default 32 KiB guidance budget; review discovery settings.")
    writes = []
    skills = []
    errors = []
    if merged != original:
        if check:
            errors.append("Workflow instruction block is missing or outdated")
        else:
            writes.append((target, merged))
    for name in SKILLS:
        source = safe_path(project, package / f"starter/.agents/skills/{name}/SKILL.md")
        content = source.read_bytes()
        destination = safe_path(project, project / f".agents/skills/{name}/SKILL.md")
        existing = read_optional(destination)
        if not destination.exists():
            status = "missing" if check else "install"
            if check:
                errors.append(f"Missing project Skill: {name}")
            else:
                writes.append((destination, content))
        elif existing == content:
            status = "unchanged"
        else:
            status = "preserved-local-version"
            warnings.append(f"Preserved existing Skill {name}; review local guidance for compatibility.")
        skills.append({"name": name, "status": status, "path": destination.relative_to(project).as_posix()})
    state_dir = safe_path(project, package / ".local")
    safe_path(project, state_dir / "backups")
    safe_path(project, state_dir / "install.json")
    result = {"status": "not-ready" if errors else ("checked" if check else ("dry-run" if dry_run else "installed")),
              "instruction_file": target.name, "skills": skills, "warnings": warnings, "errors": errors,
              "planned_changes": [path.relative_to(project).as_posix() for path, _ in writes]}
    if check or dry_run:
        return result
    backup = None
    if merged != original and target.exists():
        timestamp = datetime.now(timezone.utc).strftime("%Y%m%dT%H%M%S%fZ")
        backup_path = state_dir / "backups" / f"{target.name}.{timestamp}.bak"
        atomic_write(backup_path, original)
        backup = backup_path.relative_to(project).as_posix()
    for path, content in writes:
        atomic_write(path, content)
    result["instruction_backup"] = backup
    result["instruction_sha256"] = sha(target.read_bytes())
    result["next_step"] = "Start a new Codex chat/session at the target project root, then give your requirement normally."
    atomic_write(state_dir / "install.json", (json.dumps(result, ensure_ascii=False, indent=2) + "\n").encode())
    return result


def main() -> int:
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("--project", type=Path, help="Project containing this codex-share folder; defaults to its parent")
    mode = parser.add_mutually_exclusive_group()
    mode.add_argument("--check", action="store_true", help="Check installed files without modifying anything")
    mode.add_argument("--dry-run", action="store_true", help="Show planned installation without modifying anything")
    args = parser.parse_args()
    package = Path(__file__).resolve().parent
    try:
        result = run(package, args.project or package.parent, check=args.check, dry_run=args.dry_run)
    except (OSError, InstallError) as exc:
        print(f"Installation failed: {exc}", file=sys.stderr)
        return 2
    print(json.dumps(result, ensure_ascii=False, indent=2))
    return 1 if result["errors"] else 0


if __name__ == "__main__":
    raise SystemExit(main())
