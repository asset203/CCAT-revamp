#
# Package stage
#
FROM openjdk:17-jdk-slim AS deployment

LABEL maintainer="mashmoud.shehab@asset.com.eg"

COPY ./lookup-service.jar /usr/local/lib/lookup-service.jar

ENTRYPOINT ["java","-jar","/usr/local/lib/lookup-service.jar"]