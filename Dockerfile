FROM openjdk:8u312-oraclelinux8

#基于Java环境
VOLUME /tmp
ADD target/imagerear-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java","-jar","app.jar"]
#ENTRYPOINT ["java","-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=8888","-jar","app.jar"]

## 容器项目A访问容器B的redis失败，尝试使用主机网络模式创建容器
## docker run -d --network host --name image_rear_network_host image_rear:2.0
