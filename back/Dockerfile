FROM openjdk:17-ea-jdk-slim
ARG JAR_FILE=./build/libs/*.jar
COPY ${JAR_FILE} app.jar
EXPOSE 8800
ENTRYPOINT ["java","-jar","app.jar"]

