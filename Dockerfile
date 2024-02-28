FROM openjdk:17-jdk-alpine

MAINTAINER io.github.arch2be

COPY build/libs/order-taking-service-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java","-jar", "/app.jar"]