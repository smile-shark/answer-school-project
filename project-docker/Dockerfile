#     ^t   ^x ^v  OpenJDK  ^u^| ^c^o  ^|    ^=   ^` ^u^| ^c^o
FROM openjdk:22-jdk-slim

#   ^i  ^e Python3
RUN apt update && apt install -y python3 python3-pip && rm -rf /var/lib/apt/lists/*
RUN pip install pymysql requests asyncio aiohttp --break-system-packages

#            ^| ^{   ^u
WORKDIR /app

#   ^m ^h  Java   ^k  ^o
COPY java-app /app/java-app

#   ^m ^h  Python  ^d^z ^|
COPY python-scripts /app/python-scripts

#           ^y  ^p  ^j  ^q
CMD ["java", "-jar", "java-app", "java-app/shcool-answer-question-server.jar"]