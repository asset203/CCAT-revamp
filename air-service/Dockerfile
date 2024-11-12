FROM adoptopenjdk/openjdk11:jre-11.0.6_10-alpine AS deployment

LABEL maintainer="mashmoud.shehab@asset.com.eg"

COPY ./air-service.jar /usr/local/lib/air-service.jar
COPY ./air-commands /home/app/air-commands

ENTRYPOINT ["java","-Xms512m","-Xmx2048m", "-jar","/usr/local/lib/air-service.jar"]