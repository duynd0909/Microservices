# stage build
FROM maven:3 AS build
WORKDIR /app
COPY pom.xml /app
RUN mvn -f /app/pom.xml dependency:go-offline -B
COPY src /app/src
RUN mvn -f /app/pom.xml clean package -DskipTests

# stage run
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/*.jar /app/timesheet.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/timesheet.jar"]
