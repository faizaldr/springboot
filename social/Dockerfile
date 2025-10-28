# STAGE 1 : BUILD
# FROM menyebutkan sumber image, os alpine sangat minimal
FROM eclipse-temurin:17-jdk-alpine AS build
# di dalam container akan dijalankan pada folder yang disebutkan oleh WORKDIR
WORKDIR /app
# COPY menyalin pom.xml kedalam container pada workdir (./)
COPY pom.xml ./
# RUN menjalan perintah terminal
RUN --mount=type=cache,target=/root/.m2 \
    mvn -q -DskipTests dependency:go-offline

COPY src ./src
RUN --mount=type=cache,target=/root/.m2 \
    mvn -q -DskipTests dependency:go-offline

# setiap FROM akan dibaca sebagai awal stage
# STAGE 2 : RUNTIME
FROM eclipse-temurin:17-jdk-alpine AS runtime
WORKDIR /app

# Menambahkan user non-root
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

# Menyalin hasil dari build (file JAR) diletakkan di /app/target/*.jar app.jar
COPY --from=build /app/target/*.jar app.jar

# port untuk dapat diakses dari host
EXPOSE 8080

# env
ENV SERVER_PORT=8080

# Menjalankan perintah shell/bash
ENTRYPOINT [ "sh","-c","java $JAV_OPTS -jar app.jar --server.port=${SERVER_PORT}" ]
