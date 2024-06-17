# Builder stage
FROM gradle:8.4-jdk17-alpine AS builder
WORKDIR /app
COPY . .
#Note: Skipping tests is generally not recommended except for diagnostic purposes.
RUN gradle build --no-daemon -x test

# Final stage
FROM openjdk:17-alpine
WORKDIR /app
EXPOSE 8080

COPY --from=builder /app/build/libs/*0.0.1-SNAPSHOT.jar app.jar

VOLUME /home/dealkh/filestorage/images
VOLUME /keys
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=prod", "app.jar"]