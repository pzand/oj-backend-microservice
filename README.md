# 依赖项
- nacos 2.3.1
- rabbitmq 4.0.2
- redis 3.0.504

# 模块
- `oj-backend-common`：微服务项目公用的类 模块
- `oj-backend-gateway`：微服务网关，所有微服务的入口
- `oj-backend-judge-service`：判题服务，用于连通代码沙箱并判题
- `oj-backend-model`：微服务项目公用的数据类
- `oj-backend-question-service`：提供题目相关服务，如获取题目、更新题目信息等
- `oj-backend-service-client`：定义微服务需要的接口，用于互相通信
- `oj-backend-user-service`：提供用户服务，如用户注册、用户登录等