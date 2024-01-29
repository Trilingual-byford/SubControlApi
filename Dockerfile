FROM openjdk:17-oracle
WORKDIR /app
COPY target/SubscriptionContrApi-0.0.1-SNAPSHOT.jar .
EXPOSE 8080
ENTRYPOINT ["java","-jar","SubscriptionContrApi-0.0.1-SNAPSHOT.jar"]