# 接入与环境准备

## 普通代码项目

将 codex-share 放到目标项目根目录，执行 `python codex-share/install.py`，再新建 Codex 会话。安装器合并原生项目指令并安装两项配套技能，后续按 WORKFLOW.md 处理普通需求。

工作步骤与 FDE 一致，命令和业务规则从目标项目读取。Java/Python/Node 等项目应使用自身的源码、构建和测试入口，不套用 FDE 的目录或工具。

## FDE 的具体环境

重新运行 FDE 本身的业务验收，需要完整 FDE 源码与相关资产、项目认可的 Node/npm 和依赖、相应 Python 包。数据库测试按需要配置 TEST_DATABASE_URL；浏览器检查需要 Chromium，部分用例读取 PLAYWRIGHT_CHROMIUM_EXECUTABLE。

Windows Word 参与某些实际目录刷新、分页与 PDF 导出；缺少排版引擎不能声称这些检查通过。真实写作与检索联调按任务另行准备 CodeBuddy、模型、数据库、RAGFlow 和授权。

## 本机配置与复用

安装器不设置模型、不配置 API Key、不修改全局 Codex 配置、不自动迁移数据。默认按当前用户已可用的 Codex 环境工作。

两项 Skill 来自当前环境的已安装用户技能，原样随包提供。目标项目有同名定制时保留本地内容，并报告兼容性提醒；需要核对是否仍覆盖本次要求。特定工具能力不会随 SKILL.md 自动安装。

接入自检只验证项目规则和技能文件，完整行为由实际需求运行及其证据证明。操作入口见 START-HERE.md，加载机制见 06-codex-discovery.md。
