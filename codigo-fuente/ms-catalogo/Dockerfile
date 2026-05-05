# Etapa 1: Compilación
FROM maven:3.9.6-eclipse-temurin-21-alpine AS build
WORKDIR /app
# Copiamos solo el pom y el código fuente
COPY pom.xml .
COPY src ./src
# Compilamos y generamos el JAR
RUN mvn clean package -DskipTests

# Etapa 2: Imagen final ligera
FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app
# Traemos solo el JAR de la etapa anterior
COPY --from=build /app/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]