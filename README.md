# fortune-server

一个可直接接入 CI/CD 的 Spring Boot 项目骨架，基于：

- Java 17
- Spring Boot 3.5.13
- Maven Wrapper
- GitHub Actions
- Jenkins Pipeline
- Docker 多阶段构建

该仓库适合作为新服务的起点，默认包含：

- 一个可启动的 Spring Boot 应用
- 一个示例 REST API
- Actuator 健康检查
- Maven 测试与打包配置
- GitHub Actions 持续集成与镜像发布模板
- Jenkinsfile 流水线模板

## 目录结构

```text
.
├── .github/workflows/
│   ├── ci.yml
│   └── docker-image.yml
├── .mvn/wrapper/
├── src/
│   ├── main/
│   │   ├── java/com/example/fortuneserver/
│   │   └── resources/
│   └── test/
│       └── java/com/example/fortuneserver/
├── Dockerfile
├── Jenkinsfile
├── mvnw
├── mvnw.cmd
└── pom.xml
```

## 本地运行

### 1. 启动应用

Linux / macOS:

```bash
chmod +x mvnw
./mvnw spring-boot:run
```

Windows:

```bat
mvnw.cmd spring-boot:run
```

默认端口为 `8080`，可通过环境变量覆盖：

```bash
SERVER_PORT=9090 ./mvnw spring-boot:run
```

如果要接入真实 Tushare Token，可额外设置：

```bash
TUSHARE_TOKEN=your-token ./mvnw spring-boot:run
```

### 2. 运行测试

```bash
./mvnw clean test
```

### 3. 打包产物

```bash
./mvnw clean package
```

产物位置：

```text
target/fortune-server.jar
```

## 示例接口

### 健康检查

```bash
curl http://localhost:8080/actuator/health
```

### 服务信息

```bash
curl http://localhost:8080/api/v1/info
```

### Tushare 查询参数转换

```bash
curl -X POST http://localhost:8080/api/v1/fortune \
  -H "Content-Type: application/json" \
  -d '{"input":"帮我筛选上证的广东股票"}'
```

也可以使用更语义化的接口：

```bash
curl -X POST http://localhost:8080/api/v1/tushare/query \
  -H "Content-Type: application/json" \
  -d '{"input":"想看深圳本地的股票"}'
```

### 首页

启动后直接访问：

```bash
http://localhost:8080/
```

首页支持：

- 输入自然语言生成 Tushare `stock_basic` 查询参数
- 显示是否已配置 `TUSHARE_TOKEN`
- 展示固定筛选条件：`roe > 0`、`netProfit > 0`

## Docker

### 构建镜像

```bash
docker build -t fortune-server:local .
```

### 运行镜像

```bash
docker run --rm -p 8080:8080 fortune-server:local
```

## GitHub Actions

仓库内置两个工作流：

### 1. `ci.yml`

触发条件：

- push 到 `main`
- pull request 到 `main`

执行内容：

- 设置 Java 17
- 使用 Maven Wrapper 执行 `clean verify`
- 上传测试报告和 jar 制品

### 2. `docker-image.yml`

触发条件：

- push 到 `main`
- 打 tag（`v*`）
- 手动触发

执行内容：

- 构建 Docker 镜像
- 推送到 GitHub Container Registry

发布镜像默认名为：

```text
ghcr.io/<owner>/<repo>
```

如需使用其他镜像仓库，可修改工作流中的登录与 tag 配置。

## Jenkins

仓库根目录提供 `Jenkinsfile`，默认行为：

- 检出代码
- 使用 Maven Wrapper 执行 `clean verify`
- 归档测试报告和 jar
- 可选构建 Docker 镜像
- 可选推送镜像到远端仓库

### Jenkins 可用环境变量

- `BUILD_DOCKER=true`：启用 Docker 镜像构建
- `DOCKER_PUSH=true`：启用镜像推送
- `IMAGE_REGISTRY`：镜像仓库地址，例如 `harbor.example.com`
- `IMAGE_REPOSITORY`：镜像名，默认 `fortune-server`
- `IMAGE_TAG`：镜像 tag，默认 Jenkins `BUILD_NUMBER`
- `DOCKER_CREDENTIALS_ID`：Jenkins 中配置的用户名密码凭据 ID

### Jenkins 使用建议

如果某些节点没有 Docker 环境，保持默认即可，只跑 Java 构建和测试，不会卡在镜像阶段。

## 接入 CI/CD 的推荐方式

### GitHub 平台

1. 将仓库推送到 GitHub
2. 确保 Actions 已开启
3. PR 会自动跑 `ci.yml`
4. 合并到 `main` 后可自动构建并发布镜像

### Jenkins 平台

1. 新建 Pipeline Job
2. 指向本仓库
3. 选择 “Pipeline script from SCM”
4. 脚本路径填写 `Jenkinsfile`
5. 如需推镜像，补齐 Docker 凭据与环境变量

## 后续可扩展项

这个骨架当前保持轻量，后续可以继续扩展：

- 接入数据库与 Flyway/Liquibase
- 增加统一异常处理
- 增加 OpenAPI / Swagger
- 增加多环境配置
- 增加部署脚本或 Helm Chart
- 接入 SonarQube、制品库与部署流水线
