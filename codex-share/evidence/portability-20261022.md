# v3 迁移行为验证

日期：2026-10-22。对象是与 FDE 业务无关的隔离 Node ESM 搜索小项目，使用 Codex CLI 0.162.1、Node v24.21.0。

## 实际步骤

1. 创建具有源码、6 项原有验收断言和原项目 AGENTS.md 的独立项目。
2. 将分享文件夹复制到该项目根目录，运行一次 install.py。
3. 启动一个新的 Codex CLI 会话，仅提交 examples/portable-demo/README.md 中的普通修复需求，没有重复粘贴整套流程。
4. 核对调用日志中实际读取 WORKFLOW.md 的操作、源码修改、实际检查和 Codex 自动写入的 docs/testing 记录。
5. 独立比较原有验收断言与 Git 初始内容；仅规范化 CRLF/LF 后内容一致。原项目规则保留。
6. 独立运行原有六项检查、源码语法检查和 git diff --check。

## 结果

- Codex 调用退出码 0，完成搜索大小写、首尾空白、空查询、类型错误和输入保留行为。
- Codex 自动生成验证记录；副本见 codex-written-validation.md。
- 默认 npm test 在沙箱中因 spawn EPERM 未启动测试，Codex 如实记录该限制，并实际执行直接 Node 行为断言。
- 独立复核使用 node --test --test-isolation=none 执行原有断言：原代码 2 通过、4 失败；修复后 6 通过、0 失败、0 跳过。
- node --check src/search.mjs 与 git diff --check 退出码均为 0。
- 安装器针对空项目、已有规则、有效覆盖文件、重复安装、定制技能、缺少资产、损坏标记、预览/自检、外部重定向等边界的 16 项测试通过。

机器可读结果与原始断言结果分别见 portability-result.json、demo-before-tests.txt、demo-after-tests.txt。这里只导出隔离夹具的校验证据，不导出个人 CLI 配置、认证信息或完整会话日志。

这证明“复制 + 一次初始化 + 新会话”的流程在该独立项目中实际发生；不是“仅复制即生效”，也不是所有技术栈、业务数据或环境依赖均已验收。
