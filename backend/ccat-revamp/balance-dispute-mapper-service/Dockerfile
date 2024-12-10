#
# Package stage
#
FROM eclipse-temurin:17-jre-alpine AS deployment

LABEL maintainer="mashmoud.shehab@asset.com.eg"

COPY ./balance-dispute-mapper-service.jar /usr/local/lib/balance-dispute-mapper-service.jar

ENTRYPOINT ["java","-jar","/usr/local/lib/balance-dispute-mapper-service.jar"]