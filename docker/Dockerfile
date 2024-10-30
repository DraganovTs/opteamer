# Use the OpenJDK image as the base
FROM openjdk:21-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the JAR file into the container
COPY target/opteamer-0.0.1-SNAPSHOT.jar app.jar

# Expose the application port (adjust if necessary)
EXPOSE 8080

# Command to run the JAR file
ENTRYPOINT ["java", "-jar", "opteamer-0.0.1-SNAPSHOT.jar"]
