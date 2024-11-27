FROM eclipse-temurin:17-jre-alpine AS deployment

LABEL maintainer="mashmoud.shehab@asset.com.eg"

COPY ./ci-service.jar /usr/local/lib/ci-service.jar

ENTRYPOINT ["java","-jar","/usr/local/lib/ci-service.jar"]