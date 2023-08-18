# FROM openjdk:17-jdk-slim
# COPY target/scrape-0.0.1-SNAPSHOT.jar scrape.jar
# EXPOSE 8081
# ENTRYPOINT ["java","-jar","/scrape.jar"]

FROM maven:3.9.3-eclipse-temurin-17 AS build
RUN mkdir -p /scrape
WORKDIR /scrape
COPY pom.xml /scrape
COPY src /scrape/src
RUN mvn -f pom.xml clean package

FROM openjdk:17-jdk-slim
COPY --from=build /scrape/target/*.jar scrape.jar
EXPOSE 8081
ENTRYPOINT ["java","-jar","scrape.jar"]