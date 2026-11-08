# 公告管理新功能验收记录

## 功能范围

- 新增独立一级菜单「公告管理」，不依赖客户、库存或订单模块。
- 支持标题/状态筛选、分页、新增、编辑、草稿与发布状态切换、删除。
- 后端提供 REST 风格控制器、服务校验、MyBatis 查询与写入映射；建表 SQL 位于 `src/main/resources/db/notice-management.sql`。

## 验证

| 项目 | 状态 | 说明 |
| --- | --- | --- |
| 前后端实现 | 已完成待验证 | 新增独立页面、API、Controller、Service、Mapper 和 SQL 文件 |
| 前端 lint / 构建 | 未运行 | 当前命令执行环境持续报 `setup refresh had errors` |
| Java 编译 | 未运行 | 当前命令执行环境持续报 `setup refresh had errors` |
| 浏览器 UI / API 流程 | 未运行 | 当前命令执行环境持续报 `setup refresh had errors`；上轮确认项目未安装 Playwright |

上线前需先在目标 MySQL 数据库执行 `src/main/resources/db/notice-management.sql`，再运行前后端及浏览器流程验证。
