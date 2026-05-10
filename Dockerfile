
FROM maven:3.9.9-eclipse-temurin-17 AS build

WORKDIR /app

# Copy only pom first (for caching dependencies)
COPY pom.xml .

RUN mvn dependency:go-offline

# Now copy source code
COPY src ./src

# Build the jar
RUN mvn clean package -DskipTests


# -------- Stage 2: Run --------
FROM eclipse-temurin:17-jdk

WORKDIR /app

# Copy jar from build stage
COPY --from=build /app/target/*.jar app.jar

# Expose port (match your Spring Boot config)
EXPOSE 8081

# Run the app
ENTRYPOINT ["java", "-jar", "app.jar"]