# Utilizar una imagen base de OpenJDK
FROM openjdk:17-jdk-alpine

# Establecer el directorio de trabajo en el contenedor
WORKDIR /app

# Copiar el archivo JAR de la aplicación al contenedor
COPY target/mi-app.jar /app/mi-app.jar

# Comando para ejecutar la aplicación
CMD ["java", "-jar", "mi-app.jar"]
