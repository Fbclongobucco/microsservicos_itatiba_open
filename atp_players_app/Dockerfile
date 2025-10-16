FROM eclipse-temurin:25-jdk-alpine

WORKDIR /app

COPY target/atp_players_app-0.0.1-SNAPSHOT.jar /app/app.jar

EXPOSE 8181

CMD ["java", "-jar", "app.jar"]
