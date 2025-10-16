FROM eclipse-temurin:25-jdk-alpine

WORKDIR /app

COPY target/naming_server-0.0.1-SNAPSHOT.jar /app/naming_server.jar

EXPOSE 8761

CMD ["java", "-jar", "naming_server.jar"]
