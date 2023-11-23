FROM openjdk:17-alpine
WORKDIR /app
COPY build/libs/scheduler-service-0.0.1-SNAPSHOT.jar scheduler-service.jar
ENTRYPOINT ["java", "-jar", "scheduler-service.jar"]