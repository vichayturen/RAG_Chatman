# 检索增强生成机器人

* SpringBoot
* MyBatis
* MySQL
* Redis
* RabbitMQ
* Lucene
* Nacos
```shell
docker run -d -p 8848:8848 -p 9848:9848 -p 9849:9849 -e MODE=standalone -e PREFER_HOST_MODE=hostname -e JVM_XMS=256m -e JVM_XMX=256m --restart always --name nacos nacos/nacos-server:v2.2.3
```
