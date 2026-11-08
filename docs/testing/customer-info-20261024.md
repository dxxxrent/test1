# 客户信息管理功能验收记录

## 需求与范围

- 需求来源：用户附带的客户信息列表截图，以及“按 FDE 项目提取的 codex-share 流程新增完整功能”的要求。
- 流程依据：`codex-share/WORKFLOW.md`、`codex-share/examples/fde/README.md` 和其中的历史验收记录。FDE 文件仅用作工作步骤和证据格式参考，不作为车销管理业务规则。
- 类型：功能实现、界面交互完善。
- 目标：完成客户新增、信息列表、组合筛选、分页/排序、编辑及导出流程。

## 修改结果

- `front/src/views/customer/message.vue`：网络失败时结束加载状态；排序清除时恢复默认排序；导出当前筛选条件匹配的全部客户，不再只导出当前页；显示导出失败提示。
- `front/src/views/customer/addCustomer.vue`：新增接口失败时保留表单内容，仅在新增成功后清空。
- `front/src/api/customer.js`、`CustomerController.java`、`ICustomerService.java`、`CustomerServiceImpl.java`：增加未分页导出接口；服务端组合所有非空筛选条件、规范分页参数，并限制排序字段和方向。
- `CustomerMapper.xml`：编号、姓名、电话、身份证号筛选条件改为 AND 组合。
- `front/src/router/index.js`：新增一级「客户信息」菜单入口，直接打开现有列表页面；保留旧客户信息路由供既有链接访问，但不在「客户管理」下重复显示。
- `AGENTS.md` 和 `.agents/skills/`：通过 `codex-share/install.py` 接入项目规则与配套 Skill。

## 验收记录

| 验收项 | 命令或操作 | 状态 | 证据与限制 |
| --- | --- | --- | --- |
| codex-share 接入 | `python codex-share/install.py` | 通过 | 安装根规则入口和两项 Skill；首次安装，无原规则文件需要备份 |
| codex-share 文件完整性 | `python codex-share/install.py --check` | 通过 | 返回 `checked`，没有 warnings |
| 前端静态检查 | `npm.cmd run lint`（`front/`） | 通过 | ESLint 对 `src` 检查退出码为 0 |
| 前端生产构建 | `npm.cmd run build:prod`（`front/`） | 失败 | Webpack `html-webpack-plugin` 排序时报 `toposort` `Cyclic dependency`；未生成 `dist/index.html`，不能据此确认发布构建通过 |
| Java 编译 | `mvn -q -DskipTests compile` | 不可用 | Maven 需要从 `aliyunmaven` 下载 Spring Boot 2.1.1 父 POM，网络连接被环境拒绝；未进入 Java 编译 |
| 浏览器流程 | 项目 Playwright 检查 | 不可用 | `front/` 未安装 Playwright / `@playwright/test`；本轮没有连接后端或数据库，未覆盖真实列表、组合查询、编辑和下载操作 |
| 一级菜单导航 | `git-ui-playwright` 中的路由/菜单点检 | 未运行 | 安装项目规则后，命令执行工具持续返回 `setup refresh had errors`，无法继续启动或检查页面；新路由尚未通过浏览器验证 |
| 差异格式 | `git diff --check` | 通过 | 无空白错误 |

## 结论与后续

- 代码已实现截图对应的客户管理流程增强，但后端编译和真实 UI/API 流程尚未验证，因此不将整体验收标记为通过。
- 用户可在依赖可用的环境中重新运行 Maven 编译和 `front/` 生产构建，并连接隔离测试数据库后执行浏览器流程检查。
- 新增一级「客户信息」菜单的可见性、导航和角色过滤仍需浏览器检查。
- 按安装器提示，后续如需自动加载项目级 Codex 规则，请在项目根目录新建 Codex 会话；本轮按已读取的工作流手动执行。
