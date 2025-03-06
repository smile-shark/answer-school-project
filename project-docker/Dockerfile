# 使用官方 OpenJDK 镜像作为基础镜像
FROM openjdk:22-jdk

# 安装 Python3
RUN apt-get update && apt-get install -y python3 && rm -rf /var/lib/apt/lists/*
RUN pip install pymysql
RUN pip install datetime
RUN pip install requests
RUN pip install json
RUN pip install time
RUN pip install sys
RUN pip install os
RUN pip install asyncio
RUN pip install aiohttp

# 设置工作目录
WORKDIR /app

# 复制 Java 程序
COPY java-app /app/java-app

# 复制 Python 脚本
COPY python-scripts /app/python-scripts

# 设置容器启动命令
CMD ["java", "-jar", "java-app", "java-app/shcool-answer-question-server.jar"]