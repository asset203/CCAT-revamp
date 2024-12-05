FROM openjdk:17-jdk-slim AS deployment

LABEL maintainer="mashmoud.shehab@asset.com.eg"

COPY ./config-server.jar /usr/local/lib/config-server.jar

ENTRYPOINT ["java","-jar","/usr/local/lib/config-server.jar"]