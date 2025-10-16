from eclipse-temurin:25-jdk-alpine

WORKDIR /app

COPY target/api-gateway-0.0.1-SNAPSHOT.jar /app/api-gateway.jar

EXPOSE 8561

CMD ["java", "-jar", "api-gateway.jar"]
