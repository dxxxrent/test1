"""Portable integration checks using isolated target projects, not the FDE app."""
from __future__ import annotations

import importlib.util
from pathlib import Path
import shutil
import subprocess
import sys
import unittest
from uuid import uuid4

PACKAGE = Path(__file__).resolve().parents[1]
spec = importlib.util.spec_from_file_location("codex_share_installer", PACKAGE / "install.py")
installer = importlib.util.module_from_spec(spec)
spec.loader.exec_module(installer)


class InstallationTests(unittest.TestCase):
    def setUp(self):
        self.test_root = PACKAGE / ".local/test-temp"
        self.test_root.mkdir(parents=True, exist_ok=True)
        self.fixture_root = self.test_root / f"project-{uuid4().hex}"
        self.fixture_root.mkdir()
        self.addCleanup(self.cleanup_fixture)
        self.project = self.fixture_root / "另一个项目 with spaces"
        self.package = self.project / "codex-share"
        shutil.copytree(PACKAGE, self.package, ignore=shutil.ignore_patterns("*.zip", "*.sha256", ".local", "__pycache__", "evidence"))

    def cleanup_fixture(self):
        # Verify the absolute target before recursive fixture cleanup.
        if self.fixture_root.resolve().parent != self.test_root.resolve():
            raise RuntimeError("Fixture cleanup target escaped the test directory")
        shutil.rmtree(self.fixture_root)

    def install(self, **kwargs):
        return installer.run(self.package, self.project, **kwargs)

    def test_fresh_copy_installs_root_guidance_and_both_skills(self):
        result = self.install()
        self.assertEqual(result["status"], "installed")
        self.assertIn(b"codex-share/WORKFLOW.md", (self.project / "AGENTS.md").read_bytes())
        for name in installer.SKILLS:
            self.assertEqual((self.project / f".agents/skills/{name}/SKILL.md").read_bytes(),
                             (self.package / f"starter/.agents/skills/{name}/SKILL.md").read_bytes())
        self.assertEqual(self.install(check=True)["status"], "checked")

    def test_existing_instructions_preserved_byte_for_byte_and_backed_up(self):
        original = installer.BOM + "# Existing team rules\r\n保留规则。\r\n".encode()
        target = self.project / "AGENTS.md"
        target.write_bytes(original)
        result = self.install()
        self.assertTrue(target.read_bytes().endswith(original.removeprefix(installer.BOM)))
        self.assertTrue(target.read_bytes().startswith(installer.BOM))
        self.assertEqual((self.project / result["instruction_backup"]).read_bytes(), original)

    def test_repeated_install_is_idempotent(self):
        self.install()
        target = self.project / "AGENTS.md"
        before = target.read_bytes()
        result = self.install()
        self.assertEqual(result["planned_changes"], [])
        self.assertEqual(target.read_bytes(), before)
        self.assertEqual(before.count(installer.BEGIN), 1)

    def test_active_override_receives_rule_without_touching_agents(self):
        normal = self.project / "AGENTS.md"
        override = self.project / "AGENTS.override.md"
        normal.write_text("Normal rules", encoding="utf-8")
        override.write_text("Override rules", encoding="utf-8")
        self.assertEqual(self.install()["instruction_file"], override.name)
        self.assertEqual(normal.read_text(encoding="utf-8"), "Normal rules")
        self.assertTrue(override.read_text(encoding="utf-8").endswith("Override rules"))

    def test_empty_override_is_skipped(self):
        (self.project / "AGENTS.override.md").write_text("\n ", encoding="utf-8")
        self.assertEqual(self.install()["instruction_file"], "AGENTS.md")

    def test_different_local_skill_is_preserved(self):
        skill = self.project / f".agents/skills/{installer.SKILLS[0]}/SKILL.md"
        skill.parent.mkdir(parents=True)
        skill.write_text("Custom local Skill", encoding="utf-8")
        result = self.install()
        self.assertEqual(skill.read_text(encoding="utf-8"), "Custom local Skill")
        self.assertEqual(result["skills"][0]["status"], "preserved-local-version")
        self.assertTrue(result["warnings"])

    def test_missing_sources_cause_no_partial_installation(self):
        (self.package / f"starter/.agents/skills/{installer.SKILLS[1]}/SKILL.md").unlink()
        with self.assertRaises(OSError):
            self.install()
        self.assertFalse((self.project / "AGENTS.md").exists())
        self.assertFalse((self.project / ".agents").exists())

    def test_malformed_markers_are_rejected_without_changes(self):
        target = self.project / "AGENTS.md"
        target.write_bytes(installer.BEGIN + b"broken")
        with self.assertRaises(installer.InstallError):
            self.install()
        self.assertEqual(target.read_bytes(), installer.BEGIN + b"broken")
        self.assertFalse((self.project / ".agents").exists())

    def test_reversed_markers_are_rejected(self):
        target = self.project / "AGENTS.md"
        original = installer.END + installer.BEGIN
        target.write_bytes(original)
        with self.assertRaises(installer.InstallError):
            self.install()
        self.assertEqual(target.read_bytes(), original)

    def test_duplicate_markers_are_rejected(self):
        target = self.project / "AGENTS.md"
        original = (installer.BEGIN + installer.END) * 2
        target.write_bytes(original)
        with self.assertRaises(installer.InstallError):
            self.install()
        self.assertEqual(target.read_bytes(), original)

    def test_check_before_install_reports_missing_and_writes_nothing(self):
        self.assertEqual(self.install(check=True)["status"], "not-ready")
        self.assertFalse((self.project / "AGENTS.md").exists())
        self.assertFalse((self.package / ".local").exists())

    def test_dry_run_reports_plan_and_writes_nothing(self):
        result = self.install(dry_run=True)
        self.assertEqual(result["status"], "dry-run")
        self.assertEqual(len(result["planned_changes"]), 3)
        self.assertFalse((self.project / "AGENTS.md").exists())
        self.assertFalse((self.package / ".local").exists())

    def test_outdated_block_can_be_updated_without_changing_other_rules(self):
        target = self.project / "AGENTS.md"
        target.write_bytes(b"Before\n" + installer.BEGIN + b"\nOld\n" + installer.END + b"\nAfter")
        self.install()
        self.assertTrue(target.read_bytes().startswith(b"Before\n"))
        self.assertTrue(target.read_bytes().endswith(b"\nAfter"))
        self.assertNotIn(b"\nOld\n", target.read_bytes())

    def test_package_must_be_immediate_child(self):
        with self.assertRaises(installer.InstallError):
            installer.run(self.package, self.project.parent)
        self.assertFalse((self.project.parent / "AGENTS.md").exists())

    def test_external_redirected_skill_directory_is_rejected(self):
        outside = self.fixture_root / "outside"
        outside.mkdir()
        redirect = self.project / ".agents"
        if sys.platform == "win32":
            subprocess.run(["cmd", "/c", "mklink", "/J", str(redirect), str(outside)], check=True, capture_output=True)
        else:
            redirect.symlink_to(outside, target_is_directory=True)
        try:
            with self.assertRaises(installer.InstallError):
                self.install()
            self.assertEqual(list(outside.iterdir()), [])
            self.assertFalse((self.project / "AGENTS.md").exists())
        finally:
            if sys.platform == "win32":
                redirect.rmdir()
            else:
                redirect.unlink()

    def test_cli_check_returns_nonzero_then_zero_after_install(self):
        command = [sys.executable, str(self.package / "install.py")]
        before = subprocess.run(command + ["--check"], capture_output=True)
        self.assertEqual(before.returncode, 1)
        installed = subprocess.run(command, capture_output=True)
        self.assertEqual(installed.returncode, 0, installed.stderr)
        checked = subprocess.run(command + ["--check"], capture_output=True)
        self.assertEqual(checked.returncode, 0, checked.stderr)


if __name__ == "__main__":
    unittest.main()
