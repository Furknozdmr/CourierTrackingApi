FROM openjdk:21-jdk-slim as build

WORKDIR /app

RUN apt-get update -qq && apt-get install -y maven

COPY . .

RUN mvn clean package -DskipTests

FROM openjdk:21-jdk-slim

WORKDIR /app

COPY --from=build /app/courier-tracking-infra/target/courier-tracking-infra*.jar /courier-tracking-infra.jar

ENV TZ Europe/Istanbul

EXPOSE 9090

ENTRYPOINT ["java", "-jar", "/courier-tracking-infra.jar"]