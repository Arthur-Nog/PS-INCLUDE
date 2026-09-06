FROM eclipse-temurin:17-jdk-jammy AS build
WORKDIR /app
RUN apt-get update && apt-get install -y maven
COPY . .
RUN mvn -B clean package -DskipTests

FROM eclipse-temurin:17-jre-jammy
WORKDIR /app
EXPOSE 8080
ENV JAVA_TOOL_OPTIONS="-XX:MaxRAMPercentage=65.0 -XX:+UseSerialGC -XX:+ExitOnOutOfMemoryError"
COPY --from=build /app/target/demo-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
