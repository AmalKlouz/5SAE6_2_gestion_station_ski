#FROM ubuntu:latest
#LABEL authors="Yasmine"

#ENTRYPOINT ["top", "-b"]

FROM openjdk:17-jdk-alpine
#Definir la répertoire de travail dans le conteneur
WORKDIR /app
#Copier le fichier JAR généré dans le conteneur
COPY target/gestion-station-ski-1.0.jar /app/gestion-station-ski.jar
EXPOSE 8089

ENTRYPOINT ["java", "-jar" , "gestion-station-ski.jar"]