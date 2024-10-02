FROM amazoncorretto:22-alpine
WORKDIR /app

# Copy config phase
COPY gradle ./gradle
COPY build.gradle.kts gradle.properties gradlew settings.gradle.kts ./

# Download dependencies
RUN ["./gradlew", "--refresh-dependencies", "--no-daemon"]

# Copy the source code
COPY src ./src

# Build native executable
RUN ["./gradlew", "shadowJar", "--no-daemon"]

# Run it
ENTRYPOINT ["java", "-jar", "/app/build/libs/com.github.freitzzz.ktor-concurrent-viewers-all.jar"]
