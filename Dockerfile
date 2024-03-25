FROM openjdk:17-oracle
WORKDIR /app
COPY target/SubscriptionContrApi-1.0.0-SNAPSHOT.jar .
EXPOSE 8000
ENTRYPOINT ["java","-jar","SubscriptionContrApi-1.0.0-SNAPSHOT.jar"]