# ==========================================
# STAGE 1: Build the application
# ==========================================
FROM maven:3.9.6-eclipse-temurin-17 AS builder

# Set the working directory inside the container
WORKDIR /app

# Copy only the pom.xml first to cache dependencies
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy the actual source code and build the .jar file
COPY src ./src
RUN mvn clean package -DskipTests

# ==========================================
# STAGE 2: Run the application
# ==========================================
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Copy the compiled .jar file from the builder stage
# (Spring Boot usually names it something like target/myapp-0.0.1-SNAPSHOT.jar)
COPY --from=builder /app/target/*.jar app.jar

# Expose port 8080 (Spring Boot's default)
EXPOSE 8080

# Start the application
ENTRYPOINT ["java", "-jar", "app.jar"]