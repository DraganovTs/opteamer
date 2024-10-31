# Use the OpenJDK image as the base
FROM openjdk:21-jdk-slim

#Information for owner
# Add metadata
LABEL maintainer="DraganovTs"
LABEL version="1.0"
LABEL description="This is the Docker image for the Opteamer project."

#Add app jar to image
COPY target/opteamer-0.0.1-SNAPSHOT.jar opteamer-0.0.1-SNAPSHOT.jar

#execute the app
ENTRYPOINT ["java", "-jar", "opteamer-0.0.1-SNAPSHOT.jar"]
