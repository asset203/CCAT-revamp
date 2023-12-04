#
# Package stage
#
FROM adoptopenjdk/openjdk11:jre-11.0.6_10-alpine AS deployment

LABEL maintainer="mashmoud.shehab@asset.com.eg"

COPY ./gateway-service.jar /usr/local/lib/gateway-service.jar

ENTRYPOINT ["java","-Xms512m","-Xmx2048m", "-jar","/usr/local/lib/gateway-service.jar"]