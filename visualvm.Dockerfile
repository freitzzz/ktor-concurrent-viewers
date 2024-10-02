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

ENTRYPOINT ["java", \
    "-Dcom.sun.management.jmxremote", \
    "-Dcom.sun.management.jmxremote.port=9010", \
    "-Dcom.sun.management.jmxremote.local.only=false", \
    "-Dcom.sun.management.jmxremote.authenticate=false", \
    "-Dcom.sun.management.jmxremote.rmi.port=9010", \
    "-Dcom.sun.management.jmxremote.ssl=false", "-jar", "/app/build/libs/com.github.freitzzz.ktor-concurrent-viewers-all.jar" ]
