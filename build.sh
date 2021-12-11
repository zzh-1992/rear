#!/bin/bash

sudo mvn package

echo "=============================================="
echo "=========Begin to create docker image========="
echo "=============================================="

# 创建镜像
docker build -t rear/rear:2.0 .

# 使用镜像创建容器
docker run --name rear2.0 -d -p 8888:8080 rear/rear:2.0

echo "=============================================="
echo "============success to run server!============"
echo "=============================================="


