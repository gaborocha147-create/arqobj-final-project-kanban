FROM eclipse-temurin:25-jdk AS build
WORKDIR /build
COPY .mvn/ .mvn/
COPY mvnw pom.xml ./
RUN sed -i 's/\r$//' mvnw && chmod +x mvnw
COPY src/ src/
RUN ./mvnw -B package

FROM eclipse-temurin:25-jre
WORKDIR /app
RUN groupadd --system kanban && useradd --system --gid kanban kanban \
    && mkdir /app/data && chown kanban:kanban /app/data
COPY --from=build /build/target/kanban-0.0.1-SNAPSHOT.jar app.jar
USER kanban
ENV SPRING_PROFILES_ACTIVE=prod
EXPOSE 8080
ENTRYPOINT ["java", "-XX:MaxRAMPercentage=65.0", "-jar", "/app/app.jar"]
