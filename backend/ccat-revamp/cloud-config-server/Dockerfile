FROM adoptopenjdk/openjdk11:jre-11.0.6_10-alpine AS deployment

LABEL maintainer="mashmoud.shehab@asset.com.eg"

COPY ./config-server.jar /usr/local/lib/config-server.jar

ENTRYPOINT ["java","-jar","/usr/local/lib/config-server.jar"]