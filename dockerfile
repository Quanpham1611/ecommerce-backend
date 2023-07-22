FROM maven:3.8.3-openjdk-17 AS build

COPY . .

RUN mvn package

FROM openjdk:17-jdk-slim

EXPOSE 8080

COPY --from=build /target/demo-1.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
