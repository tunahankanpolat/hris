FROM maven:3.9.3-eclipse-temurin-17 AS build
RUN mkdir -p /hris
WORKDIR /hris
COPY pom.xml .
COPY src src
RUN mvn -f pom.xml clean package -DskipTests

FROM openjdk:17-jdk-slim
WORKDIR /hris
COPY --from=build /hris/target/*.jar hris.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","hris.jar"]