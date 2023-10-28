# ssm-web 服务端

## 配置Server

### JDBC

新建`application-jdbc.yaml`在资源目录下

```yaml
jdbc:
  driverClassName: com.mysql.cj.jdbc.Driver
  url: jdbc:mysql://127.0.0.1:3306/ # 数据库
  username: username
  password: password
```

### 服务器数据

新建`application-mem-file.yaml`在资源目录下

```yaml
web:
  dir: web/dist #web目录，静态资源
  data-dir: data # 数据文件，用户上传的文件等等
```
### Token配置

新建`application-token.yaml`在资源目录下

```yaml
token:
  pwd: password #PBE密码
  alive-time: 30m # 默认有效时长，默认30m
```