# Utiliza la imagen oficial de Maven como base
FROM maven:3.8.4-openjdk-17 AS build

  # Establece el directorio de trabajo en la carpeta backend de tu proyecto
WORKDIR /app/backend

  # Copia el archivo pom.xml y el resto de los archivos de la aplicación
COPY ./pom.xml ./
COPY ./src ./src/

  # Ejecuta Maven para compilar y empaquetar la aplicación
RUN mvn clean package -DskipTests

  # Utiliza una imagen base de OpenJDK para ejecutar la aplicación
FROM openjdk:17-jdk-alpine

  # Establece el directorio de trabajo en la raíz
WORKDIR /

  # Copia el archivo JAR generado desde la etapa de construcción anterior
COPY --from=build /app/backend/target/*.jar app.jar

  # Expone el puerto en el que la aplicación se ejecutará
EXPOSE 8443

  # Comando para ejecutar la aplicación Spring Boot
CMD ["java", "-jar", "app.jar"]
