FROM maven:3.9.9-eclipse-temurin-21-alpine AS builder
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline -B
COPY src ./src
RUN mvn clean verify
FROM bellsoft/liberica-runtime-container:jdk-21-slim-musl
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar
CMD java -jar -Dspring.profiles.active=${PROFILE} app.jar

