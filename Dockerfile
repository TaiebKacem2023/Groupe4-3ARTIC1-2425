# Use a lightweight OpenJDK image as base
FROM openjdk:17-jdk-alpine

# Set a working directory inside the container
WORKDIR /app

# Copy the built jar file into the container
COPY target/Foyer-1.4.0-SNAPSHOT.jar app.jar

# Expose the port your Spring Boot app runs on (default 8080)
EXPOSE 8086

# Run the jar file
ENTRYPOINT ["java", "-jar", "app.jar"]
