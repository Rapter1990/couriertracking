# Stage 1: Build stage
FROM gradle:8.9.0-jdk21 AS build

# Set working directory
WORKDIR /app

# Copy Gradle wrapper and build files
COPY gradlew .
COPY gradle gradle

# Copy project files
COPY build.gradle.kts settings.gradle.kts ./
COPY src src

# Build the project and create the executable JAR
RUN ./gradlew clean build -x test

# Stage 2: Run stage
FROM amazoncorretto:21

# Set working directory
WORKDIR couriertracking

# Copy the JAR file from the build stage
COPY --from=build /app/build/libs/*.jar couriertracking.jar

# Expose port 1231
EXPOSE 1231

# Set the entrypoint command for running the application
ENTRYPOINT ["java", "-jar", "couriertracking.jar"]
