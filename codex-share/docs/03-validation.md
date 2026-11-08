# FDE 校验命令与证据

以下为已有项目结构和记录中的命令，不是本次打包已执行的测试。由 Codex 按任务影响挑选，并以当前源码及环境为准。

## 基础检查

在 FDE 仓库根目录查看范围：

```powershell
git status --short --branch
git diff --stat
git diff
git diff --cached
git diff --check
```

`git diff` 不展示未跟踪文件正文，需另外读取与本轮相关的新文件。已有未提交改动也必须识别，不默认全部属于本条任务。

在 `demo/codebuddy-bridge/` 下：

```powershell
npx tsc -p tsconfig.json --noEmit
# 前端脚本改动时，按实际文件选择：
node --check public/product.js
# 需要构建验证时：
npm run build
```

build 的 prebuild 会运行项目已有 SDK 补丁脚本，因此先阅读现有启动与构建说明。package.json 没有独立 lint 命令，类型检查不能称为 lint。

## 定向功能检查示例

以下文件来自私有文档删除的现有验证范围：

```powershell
node --import tsx --test test/service-recommendations.test.ts test/workspace-tree.test.ts test/requirement-analysis-jobs.test.ts
```

这些用例针对对应功能，不能替代其他需求的验收。必要时使用 `npm test` 扩展到 Bridge 全量；是否执行取决于本次修改与尚未解决的问题。

## 数据库、HTTP 与 UI 示例

环境已配置好测试数据库和浏览器后，在 Bridge 下选择相应场景：

```powershell
node --import tsx --test test/service-recommendations-http.test.ts test/requirement-analysis-http.test.ts
```

这些现有用例读取 `TEST_DATABASE_URL`；未设置时会 skip。测试进程退出为 0 仍需核对跳过情况。相关用例读取浏览器路径配置并使用隔离数据库 schema；测试创建与清理行为应以实际源码为准。

按照 `git-ui-playwright`，核对用户操作后的结果，必要时记录桌面/窄屏、键盘、只读与错误状态，收集页面错误和必要截图。打开页面本身不构成功能通过。

## Python、插件和文档

Python 语法检查可用 ast.parse 对改动脚本进行静态解析；语法通过不代表渲染或业务行为正确。插件结构变更时，在根目录使用：

```powershell
python scripts/validate_codebuddy_bundle.py codebuddy/gov-doc-writing
```

文档变更还需按本次需求检查实际 DOCX、目录/页码、正文/表格和排版输出。`examples/fde/records/newbuild-toc-20261021.md` 展示了没有新增实现测试、但核对真实 Word 文档和全部页面的记录方式；它是历史证据，不能作为新改动通过的证明。

## 记录口径

区分通过、失败、跳过、不可用和未运行，写清模型/数据库/服务使用真实依赖还是替身。失败保留原因和复现步骤，修复后记录新检查。对应日志和截图未包含在分享包时，明确其缺失，不能把示例报告当作可独立复核的全部证据。
