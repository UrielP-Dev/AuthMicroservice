FROM ubuntu:latest
FROM openjdk:17-jdk-alpine
LABEL authors="Uriel-PP"
EXPOSE 8081
ENV SPRING_DATA_MONGODB_URI=""
ADD ./target/auth-service-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]