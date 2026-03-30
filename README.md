# fortune-server

一个基于 Java 11、Spring Boot 2.6.13 和 Maven 的多模块后端示例工程。

## 模块说明

- `common-service`
  - 放置公共枚举、统一响应对象、分页模型等通用能力。
- `major-service`
  - 当前核心业务模块。
  - 提供系统配置查询接口、统一异常处理和基础服务分层。
- `gateway-service`
  - 当前仅保留为预留模块，已统一到父 POM 管理。

## 当前接口

`major-service` 默认端口为 `8098`，提供以下接口：

- `GET /api/users/ping`
  - 健康检查接口
- `GET /api/users/configs`
  - 查询全部系统配置
- `GET /api/users/configs/{variable}`
  - 按配置键查询单条系统配置

统一返回结构使用 `BeeResponseEntity`：

- `code`：业务状态码
- `data`：响应数据
- `msg`：响应消息

## 本地运行

### 1. 配置环境变量

`major-service` 使用环境变量读取数据库配置：

```bash
export DB_URL='jdbc:mysql://127.0.0.1:3306/sys?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC&useSSL=false&tinyInt1isBit=false'
export DB_USERNAME='root'
export DB_PASSWORD='password'
```

可选环境变量：

```bash
export MAJOR_SERVICE_PORT=8098
export SPRING_APPLICATION_NAME=fortune-service
```

### 2. 启动 major-service

```bash
mvn -pl major-service spring-boot:run
```

### 3. 运行测试

```bash
mvn test
```

`major-service` 测试默认启用 `test` profile，并关闭数据源自动装配，避免在无数据库环境下启动失败。

## 已完成的基础完善

- 为 `major-service` 增加基础 `service` 分层
- 增加系统配置查询接口
- 接口统一返回 `BeeResponseEntity`
- 增加全局异常处理
- 将数据库配置调整为环境变量驱动
- 修正 `gateway-service` 未继承父 POM 的结构问题
- 清理根 POM 中误导性的主启动类配置
