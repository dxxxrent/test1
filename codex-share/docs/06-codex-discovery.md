# 为什么需要一次接入

Codex 在每次运行或会话开始时发现项目指令，从项目根到当前目录读取适用 AGENTS 文件。每个目录优先非空 AGENTS.override.md，其次 AGENTS.md；更具体目录的指令可以覆盖较上层内容。

因此把 codex-share 放在项目普通子目录，并不会让它里面的 Markdown 自动约束父项目的代码任务。安装器把短入口写到根目录有效指令文件，入口再明确要求读取 WORKFLOW.md。

配套 Skills 放到项目 .agents/skills/ 后，可以由任务描述匹配或显式调用；仅把 Skill 保存在分享包的普通嵌套目录不等于完成项目级发现。

安装后启动新会话。已有会话中的指令、禁用技能配置、全局/子目录覆盖规则及指令预算限制，仍会影响最终加载。安装器遇到同名定制会保留内容，遇到默认 32 KiB 预算风险会提醒，最终以当前环境的实际加载为准。

可先在新会话中询问：“列出当前项目采用的修改、校验和记录步骤，以及规则来源。”这只能检查加载；还需实际需求修改和对应证据来验证行为。

官方依据（2026-10-22 核对）：

- [Custom instructions with AGENTS.md](https://learn.chatgpt.com/docs/agent-configuration/agents-md)
- [Build skills](https://learn.chatgpt.com/docs/build-skills)
