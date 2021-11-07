FROM maven:3.8.2-openjdk-11 AS maven
WORKDIR /java
COPY . /java
ENV MONGO_DB=mydrafts
ENV MONGO_USER=user
ENV MONGO_PASS=pass
ENV MONGO_HOST=localhost
ENV API_KEY=KEY-API-TMDB
RUN mvn clean package

FROM openjdk:11.0.10-jdk-slim
WORKDIR /app
ENV PORT=8080
COPY --from=builder /java/target/api-mydrafts-0.0.1-SNAPSHOT.jar /app
ENTRYPOINT java -Dserver.port=8888 -jar api-mydrafts-0.0.1-SNAPSHOT.jar
EXPOSE PORT