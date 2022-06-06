# MarsCTF

Vue+Springboot开发的CTF学习平台，提供动态靶机、学习模块、writeup模块等等CTF平台的核心功能。提供docker版本。

![image-20220605194440513](.assert/image-20220605194440513.png)



# 快速启动

**初始管理员用户名/密码：admin/123456**

下载 [https://github.com/b1ackc4t/MarsCTF/releases](https://github.com/b1ackc4t/MarsCTF/releases) 里已经打包好的docker包，解压后执行以下命令：

``````bash
chmod -R 755 ./marsctf-docker  # 赋予足够权限
cd ./marsctf-docker
docker-compose up -d
``````

然后访问 **[http://127.0.0.1:7991]()** 即可，使用其他ip无法访问后端服务。

如要修改访问ip，请修改```/marsctf-docker/nginx/html/config.json```的```BASE_URL_PROD```，然后重启服务即可

## 动态靶机支持

### 单机部署

配置很简单，首先开启docker API（需要和平台在同一台机器上）

在后台平台配置处配置红框内的参数保存即可，容器返回IP即云服务器IP，docker API的ip推荐使用云服务器的内网ip或者docker0网卡IP

![image-20220606130714165](.assert/image-20220606130714165.png)

### 多机部署

* 针对希望把docker API服务器放在其他机器上和平台分开的情况。通常关闭即可

![image-20220606131444296](.assert/image-20220606131444296.png)

原理：使用frp将docker API服务器上开启的容器端口映射到平台服务器上，端口号不改。如：192.168.48.139:34567 -> 106.54.134.134:34567

docker API服务器上开启frpc，并开启frpc API

平台服务器上开启frps

随后根据配置填写对应参数即可

### 配置TLS

* 针对内网不安全的情况，增加证书认证。通常关闭即可。

![image-20220606131023158](.assert/image-20220606131023158.png)

对于**debian系的linux**，已经提供了便携的脚本配置

首先修改```./marsctf-docker/docker-tls/gen_key.sh```里的IP、密码以及其他元数据信息

```
# -------------------------------------------------------------
# 自动创建 Docker TLS 证书
# -------------------------------------------------------------
# 以下是配置信息
# --[BEGIN]------------------------------
IP="192.168.48.202"	# 改为云服务器IP
PASSWORD="b1ackc4tyyds"	# 用于docker-api通信的密钥
# 以下可改可不改
COUNTRY="CN"
STATE="HUBEI"
CITY="WUHAN"
ORGANIZATION="XXSSHH"
ORGANIZATIONAL_UNIT="Dev"
EMAIL="xxxxxxxxx@163.com"
```

改后保存，执行

```bash
cd ./marsctf-docker/docker-tls
chmod +x ./gen_key.sh
chmod +x ./startup.sh
./startup.sh
```

脚本会自动完成开启docker-api、产生tls密钥、配置tls密钥一系列步骤。

---

对于其他linux或者以上脚本失效的情况，则自行配置打开docker api，并配置tls证书，将生成的客户端```ca.pem、ca-key.pem、cert.pem、key.pem```（名称固定，不然识别不到）复制到```./marsctf-docker/main/CertKey```，然后重启docker服务即可

# 功能预览

1. 学习模块
   * 提供体系化学习资料，并和挑战中的CTF题目相关联
   
   ![image-20220606144449132](.assert/image-20220606144449132.png)
   
   ![image-20220606144500774](.assert/image-20220606144500774.png)
   
2. 挑战模块
   * 提供CTF题目，支持动态靶机
   * 题目类型、标签均可扩展
   
   ![image-20220606144549736](.assert/image-20220606144549736.png)
   
   ![image-20220606144520523](.assert/image-20220606144520523.png)
   
3. writeup模块
   * 分享解题思路的平台
   
   ![image-20220606144606804](.assert/image-20220606144606804.png)
   
4. 公告、排行榜等其他功能



# 声明

此项目为本人在湖北大学的本科毕业设计，湖北大学已申请了软件著作权

