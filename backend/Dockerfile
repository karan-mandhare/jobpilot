FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

COPY target/jobpilotapplication-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["sh", "-c", "java -jar app.jar --server.port=${PORT:-8080}"]