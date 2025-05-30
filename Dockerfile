FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY . .
RUN mvn clean test

FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY --from=build /app .
CMD ["mvn", "test"]
