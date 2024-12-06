#imagem base 
FROM ubuntu:latest AS build

#instalar dentro do container jdk17
RUN apt-get update
RUN apt-get install openjdk-17-jdk -y
COPY . .
#instalar maven
RUN apt-get install maven -y
RUN mvn clean install

#para rodar a api
FROM openjdk:17-jdk-slim

EXPOSE 8080

#copio do projeto para o container
COPY --from=build /target/dscomercio-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"] 