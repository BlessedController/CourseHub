FROM openjdk:21 AS build

WORKDIR /coursehub-api-gateway

COPY pom.xml mvnw ./
COPY .mvn .mvn

RUN chmod +x mvnw
RUN ./mvnw dependency:resolve

COPY src src
RUN ./mvnw package -DskipTests

FROM openjdk:21

WORKDIR /app
COPY --from=build /coursehub-api-gateway/target/*.jar api-gateway.jar

ENTRYPOINT ["java", "-jar", "api-gateway.jar"]
