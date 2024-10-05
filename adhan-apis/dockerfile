# Use an official JDK runtime as a parent image
FROM openjdk:21

# Set the working directory inside the container
WORKDIR /app

# Copy the Maven project’s JAR file to the container (this happens during the build)
# First, we'll package the app using Maven, then copy the generated JAR to the Docker image.
COPY adhan-apis/target/adhan-apis-0.0.1-SNAPSHOT.jar app.jar

# Expose the port your Spring Boot app runs on (usually 8080)
EXPOSE 8080

# Run the JAR file
ENTRYPOINT ["java", "-jar", "app.jar"]
