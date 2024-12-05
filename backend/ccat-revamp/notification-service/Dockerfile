#
# Package stage
#
FROM eclipse-temurin:17-jre-alpine AS deployment

LABEL maintainer="mashmoud.shehab@asset.com.eg"

COPY ./notification-service.jar /usr/local/lib/notification-service.jar

ENTRYPOINT ["java","-jar","/usr/local/lib/notification-service.jar"]