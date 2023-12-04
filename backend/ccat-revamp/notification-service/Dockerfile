#
# Package stage
#
FROM adoptopenjdk/openjdk11:jre-11.0.6_10-alpine AS deployment

LABEL maintainer="mashmoud.shehab@asset.com.eg"

COPY ./notification-service.jar /usr/local/lib/notification-service.jar

ENTRYPOINT ["java","-jar","/usr/local/lib/notification-service.jar"]