FROM maven:3.9.8-eclipse-temurin-21 AS build
WORKDIR /app

COPY .mvn .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:go-offline -B

COPY src ./src
RUN ./mvnw clean package -DskipTests -B

FROM eclipse-temurin:21-jre
WORKDIR /app

RUN groupadd -r spring && useradd -r -g spring spring

COPY --from=build /app/target/*.jar app.jar

RUN chown spring:spring app.jar

USER spring:spring

EXPOSE 4000

ENTRYPOINT ["java", "-jar", "app.jar"]