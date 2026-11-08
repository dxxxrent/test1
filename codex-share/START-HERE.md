# 复制后如何使用

## 一次性接入

1. 将整个 codex-share 文件夹放在目标代码项目根目录，保持目录名为 codex-share。
2. 在目标项目根目录执行 `python codex-share/install.py`。安装器使用 Python 标准库，建议 Python 3.11+；不安装第三方包。
3. 查看返回的 instruction_file、skills 和 warnings，再运行 `python codex-share/install.py --check`。
4. 在目标项目根目录新建 Codex 聊天/会话，让新规则进入启动时的指令链。
5. 直接提出普通需求，例如“修复查询大小写和首尾空格处理”，无需再粘贴全套流程提示词。

也可以在目标项目的 Codex 聊天中说：“请运行 codex-share/install.py，将这套流程接入当前项目，保留原规则。”由 Codex 执行安装；安装后仍应新建会话。

只有复制动作、未运行接入，不会使隐藏在普通子目录里的规则自动作用于父项目。已经启动的会话可能仍使用旧指令，需要重新开启。

## 安装器做什么

- 根目录存在非空 AGENTS.override.md 时合入该文件，否则合入 AGENTS.md。
- 使用带标记的短规则引用 codex-share/WORKFLOW.md，保留规则块之外的内容。
- 原有指令文件变化前将原文备份到 codex-share/.local/backups/。
- 将两项配套 Skill 放入项目 .agents/skills/；同名内容不同时保留并提醒核对，不覆盖定制。
- 写入本机 codex-share/.local/install.json，重复安装不重复添加规则块。

安装器不修改个人全局设置、项目业务源码、测试命令和环境文件。`.local/` 是本机状态，压缩包不携带它；复制已用过的分享文件夹时也应排除它。

## 接入前预览

```powershell
python codex-share/install.py --dry-run
```

预览不写入文件。安装文件已接入后的只读检查使用 --check；检查缺失时返回非零值。

## 后续每个需求

Codex 读取目标项目约定与代码，完成必要修改，按实际技术栈执行校验，复核结果，再更新现有任务记录或 docs/testing/。复杂计划按需放 docs/plans/；只做咨询时不额外生成记录文件。

缺少依赖、工具不可用或检查跳过时如实记录。需要 UI、文档、数据库或真实模型的场景必须有对应环境和本轮授权。

## 已有项目规则

项目更具体的目录指令、全局指令和用户本轮要求仍然适用。存在冲突、禁用技能或指令大小限制时先核对实际加载来源；官方机制见 docs/06-codex-discovery.md。不能用接入检查的成功替代所有项目、所有检查均已运行的结论。
