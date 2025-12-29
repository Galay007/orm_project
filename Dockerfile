FROM bellsoft/liberica-runtime-container:jdk-21-slim-musl
WORKDIR /app
COPY target/*.jar app.jar
CMD java -jar -Dspring.profiles.active=${PROFILE} app.jar

