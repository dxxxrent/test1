# CodeBuddy 项目约定

本仓库的政务写作专家团位于 `codebuddy/gov-doc-writing/`，是一个版本为
`1.9.2` 的 CodeBuddy 项目插件。涉及政务公文、信息化立项方案、模板、审批
细则、预算、架构图或 DOCX 时，先按以下顺序工作：

1. 从仓库根目录加载插件：
   `codebuddy --plugin-dir ./codebuddy/gov-doc-writing --agent gov-doc-writing-team-lead`。
2. 由 `gov-doc-writing-team-lead` 先生成并校验 `_work/task-routing.json`，再按
   路由调度成员；缺资料或事实冲突时输出阻塞清单，不直接补写。
3. 默认使用 Sub-agent。只有当前会话明确启用 Agent Teams、阶段输入互相独立、
   结果可用 Schema/脚本校验且可重跑时，才启用阶段内并行；失败时回退顺序调用。
4. 所有阶段结果写入 `_work/`，平台或主理人是唯一状态推进者。成员不能改变
   权限、RAGFlow 数据集范围、用户等待状态或事实冻结结果。
5. 包内 Python 脚本统一使用 `gov-doc-python`；插件不包含 RAGFlow 地址、API key、
   `dataset_id`、用户资料或运行产物。Headless 平台执行必须使用任务级
   `--mcp-config`、`--strict-mcp-config` 和最小工具 allowlist。

详细的插件用法见 [`codebuddy/README.md`](codebuddy/README.md)；开发方案评估、
官方依据、Gate 和未验证假设见
[`docs/research/codebuddy-development-plan-evaluation.md`](docs/research/codebuddy-development-plan-evaluation.md)。

