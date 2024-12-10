#
# Package stage
#
FROM eclipse-temurin:17-jre-alpine AS deployment

LABEL maintainer="mashmoud.shehab@asset.com.eg"

COPY ./nba-service.jar /usr/local/lib/nba-service.jar


ENTRYPOINT ["java","-jar","/usr/local/lib/nba-service.jar"]