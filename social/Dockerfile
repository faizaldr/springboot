# STAGE 1 : BUILD
# FROM menyebutkan sumber image, os alpine sangat minimal
FROM eclipse-temurin:17-jdk-alpine AS build
# di dalam container akan dijalankan pada folder yang disebutkan oleh WORKDIR
WORKDIR /app
# COPY menyalin pom.xml kedalam container pada workdir (./)
COPY mvnw ./
COPY .mvn .mvn
COPY pom.xml ./

RUN chmod +x mvnw \
    && sed -i 's/\r$//' mvnw
# mengganti karakter \r (ganti baris) dari windows CRLF menjadi linux LF


# RUN menjalan perintah terminal
RUN --mount=type=cache,target=/root/.m2 \
    ./mvnw -q -DskipTests dependency:go-offline

COPY src ./src
RUN --mount=type=cache,target=/root/.m2 \
    ./mvnw -q -DskipTests clean package

# setiap FROM akan dibaca sebagai awal stage
# STAGE 2 : RUNTIME
FROM eclipse-temurin:17-jdk-alpine AS runtime
WORKDIR /app


# Menambahkan user non-root
RUN addgroup -S spring && adduser -S spring -G spring

# Menyalin hasil dari build (file JAR) diletakkan di /app/*.jar app.jar
COPY --from=build /app/target/*.jar /app/app.jar

RUN mkdir -p /app/uploads \
    && chmod 755 /app/uploads \
    && chown -R spring:spring /app/uploads

#berganti menjadi user spring
USER spring:spring

# port untuk dapat diakses dari host
EXPOSE 8080

# env
ENV SERVER_PORT=8080

# Menjalankan perintah shell/bash
ENTRYPOINT [ "sh","-c","java $JAV_OPTS -jar ./app.jar --server.port=${SERVER_PORT}" ]
