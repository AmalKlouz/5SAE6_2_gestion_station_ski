FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY gestion-station-ski/target/*.jar /app/app.jar
EXPOSE 8089
ENTRYPOINT ["java", "-jar", "/app/app.jar"]