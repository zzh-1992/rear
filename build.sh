#!/bin/bash

echo "=============================================="
echo "=========Begin to create docker image========="
echo "=============================================="

# 删除原有镜像
docker rmi rear/rear:2.0

# 停止原有容器
docker stop rear2.0
# 删除原有容器
docker rm rear2.0

# 创建新镜像
docker build -t rear/rear:2.0 .

# 使用镜像创建容器
docker run --name rear2.0 -d -p 8888:8080 rear/rear:2.0

echo "=============================================="
echo "============success to run server!============"
echo "=============================================="
