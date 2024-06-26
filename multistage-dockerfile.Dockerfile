# Stage 1: Construir la aplicación Vue.js
FROM node:16.14.2-alpine AS vue-build
WORKDIR /app
COPY frontend/enterprise_event_solution /app
RUN npm install
RUN npm run build
RUN npm run copy-dist-to-spring
# Stage 2: Construir el backend de Spring Boot
FROM maven:3.8.4-openjdk-17 AS spring-build
WORKDIR /app
COPY backend/ /app
COPY --from=vue-build /app/dist /app/src/main/resources/static
RUN mvn clean package -DskipTests

# Stage 3: Crear la imagen final
FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY --from=spring-build /app /app
EXPOSE 8443
CMD ["java", "-jar", "target/EnterPriseEventSolutions-0.0.1-SNAPSHOT.jar"]