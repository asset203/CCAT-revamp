FROM eclipse-temurin:17-jre-alpine AS deployment

LABEL maintainer="mashmoud.shehab@asset.com.eg"

COPY ./dynamic-page-service.jar /usr/local/lib/dynamic-page-service.jar

ENTRYPOINT ["java","-jar","/usr/local/lib/dynamic-page-service.jar"]