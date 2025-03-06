FROM openjdk:22-jdk-slim

RUN apt update && apt install -y python python-pip && rm -rf /var/lib/apt/lists/*
RUN pip install pymysql requests asyncio aiohttp --break-system-packages

WORKDIR /app

COPY java-app /app/java-app

COPY python-scripts /app/python-scripts

CMD ["java","-jar","java-app/shcool-answer-question-server.jar"]
