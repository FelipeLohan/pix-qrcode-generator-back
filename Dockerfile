# ── Build ────────────────────────────────────────────────────────────────────
FROM eclipse-temurin:17-jdk-alpine AS builder

WORKDIR /app

COPY .mvn   .mvn
COPY mvnw   pom.xml ./
RUN ./mvnw dependency:go-offline -q

COPY src ./src
RUN ./mvnw package -DskipTests -q

# ── Runtime ──────────────────────────────────────────────────────────────────
FROM eclipse-temurin:17-jre-alpine

RUN apk add --no-cache nginx netcat-openbsd

WORKDIR /app

COPY --from=builder /app/target/*.jar app.jar
COPY nginx.conf            /etc/nginx/nginx.conf
COPY docker-entrypoint.sh  /docker-entrypoint.sh

RUN chmod +x /docker-entrypoint.sh \
 && mkdir -p /run/nginx

EXPOSE 8081

ENTRYPOINT ["/docker-entrypoint.sh"]
