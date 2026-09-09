# Stage 1: Build the Java application
FROM eclipse-temurin:17-jdk AS builder
WORKDIR /app
COPY src/ ./src
RUN mkdir -p bin && javac -d bin src/App.java

# Stage 2: Minimal Runtime image
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=builder /app/bin ./bin

EXPOSE 8080

CMD ["java", "-cp", "bin", "App"]