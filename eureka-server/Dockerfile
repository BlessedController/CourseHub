FROM openjdk:21 AS build

WORKDIR /coursehub-eureka-server

COPY pom.xml mvnw ./
COPY .mvn .mvn

RUN chmod +x mvnw
RUN ./mvnw dependency:resolve

COPY src src
RUN ./mvnw package -DskipTests

FROM openjdk:21

WORKDIR /app
COPY --from=build /coursehub-eureka-server/target/*.jar eureka-server.jar

ENTRYPOINT ["java", "-jar", "eureka-server.jar"]
