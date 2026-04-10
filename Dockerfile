# ---- Stage 1: Build ----
FROM eclipse-temurin:21-jdk AS builder
WORKDIR /build

COPY .mvn/ .mvn/
COPY mvnw pom.xml ./
RUN ./mvnw dependency:go-offline -B

COPY src/ src/
RUN ./mvnw package -DskipTests -B && \
    mv target/fortune-server-*.jar target/app.jar

# ---- Stage 2: Runtime ----
FROM eclipse-temurin:21-jre
WORKDIR /app

RUN addgroup --system appgrp && adduser --system --ingroup appgrp appuser
COPY --from=builder /build/target/app.jar app.jar
RUN chown appuser:appgrp app.jar

USER appuser
EXPOSE 8080

HEALTHCHECK --interval=30s --timeout=3s \
  CMD curl -f http://localhost:8080/actuator/health || exit 1

ENTRYPOINT ["java", "-XX:+UseContainerSupport", "-jar", "app.jar"]
