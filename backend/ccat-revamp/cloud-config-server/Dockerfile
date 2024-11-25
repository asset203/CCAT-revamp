FROM eclipse-temurin:17.0.4.1_1-jre-alpine AS deployment

LABEL maintainer="mashmoud.shehab@asset.com.eg"

COPY ./config-server.jar /usr/local/lib/config-server.jar

ENTRYPOINT ["java","-jar","/usr/local/lib/config-server.jar"]