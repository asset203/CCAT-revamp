FROM  registry.redhat.io/openjdk/openjdk-11-rhel7

LABEL maintainer="mashmoud.shehab@asset.com.eg"

COPY ./target/gateway-service.jar /usr/local/lib/gateway-service.jar

ENTRYPOINT ["java","-jar","/usr/local/lib/gateway-service.jar"]