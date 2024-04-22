#FROM azul/zulu-openjdk:17-latest
#VOLUME /tmp
#COPY build/libs/*.jar app.jar
#ENTRYPOINT ["java","-jar","/app.jar"]


# Etapa de construcción
FROM gradle:jdk17-jammy AS build

# Copiar los archivos de configuración de Gradle al contenedor
COPY --chown=gradle:gradle build.gradle settings.gradle /home/gradle/src/

# Copiar el código fuente de la aplicación
COPY --chown=gradle:gradle src /home/gradle/src/src

# Establecer el directorio de trabajo
WORKDIR /home/gradle/src

# Ejecutar la construcción de Gradle, sin ejecutar las pruebas
RUN gradle build --no-daemon -x test

# Etapa de ejecución
FROM azul/zulu-openjdk:17-latest

# Copiar el artefacto de construcción desde la etapa de construcción al directorio de trabajo del contenedor de ejecución
COPY --from=build /home/gradle/src/build/libs/bordify-monolith-0.0.1-SNAPSHOT.jar app.jar

# Exponer el puerto 8081
EXPOSE 8081

# Definir el punto de entrada para ejecutar la aplicación
ENTRYPOINT ["java","-jar","/app.jar"]
