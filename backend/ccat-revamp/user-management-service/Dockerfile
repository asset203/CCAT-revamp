#
# Package stage
#
FROM eclipse-temurin:17-jre-alpine AS deployment

LABEL maintainer="mashmoud.shehab@asset.com.eg"

COPY ./user-management-service.jar /usr/local/lib/user-management-service.jar

ENTRYPOINT ["java","-jar","/usr/local/lib/user-management-service.jar"]