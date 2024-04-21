# Use a Maven build image to compile the Java application
FROM maven:3.8.4-openjdk-17-slim AS build

# Copy the source code into the container
COPY . /app

# Set the working directory to the location of the pom.xml file
WORKDIR /app

# Build the Java application using Maven
RUN mvn clean install -DskipTests

# Use a Java runtime as a base image
FROM openjdk:17-alpine

# Set the working directory in the container
WORKDIR /app

# Copy the compiled Java application JAR file from the Maven build stage into the container
COPY --from=build /app/target/matchflick-0.0.1-SNAPSHOT.jar /app/matchflick-0.0.1-SNAPSHOT.jar

# Expose the port on which your Java application will run
EXPOSE 8080

# Command to run the Java application when the container starts
CMD ["java", "-jar", "matchflick-0.0.1-SNAPSHOT.jar"]
