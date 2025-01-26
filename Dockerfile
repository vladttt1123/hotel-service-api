# Use an official Maven image to build the app
FROM maven:3.8.6-openjdk-17-slim AS build

# Set the working directory
WORKDIR /app

# Copy the pom.xml and the src folder (to be able to leverage Docker cache)
COPY pom.xml .
COPY src ./src

# Run the Maven build process to compile the app
RUN mvn clean install -DskipTests

# Use a lightweight OpenJDK image to run the app
FROM openjdk:17-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the jar file from the build process
COPY target/*.jar /app/app.jar

# Expose port 8080 for the application
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "/app/app.jar"]