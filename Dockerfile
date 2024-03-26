FROM openjdk:17-oracle
WORKDIR /app
COPY target/SubscriptionContrApi-1.0.0-prod-8080.jar .
EXPOSE 8080
ENTRYPOINT ["java","-jar","SubscriptionContrApi-1.0.0-prod-8080.jar"]