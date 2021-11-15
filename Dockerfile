FROM maven:3.8.3-jdk-11-slim AS maven
WORKDIR /java
COPY . /java
ENV MONGO_DB=mydrafts
ENV MONGO_USER=user
ENV MONGO_PASS=pass
ENV MONGO_HOST=localhost
ENV API_KEY=KEY
RUN mvn clean package -DskipTests

FROM openjdk:11.0.10-jre-slim
WORKDIR /app
ENV PORT=8080
COPY --from=maven /java/target/api-mydrafts-0.0.1-SNAPSHOT.jar /app/api.jar
CMD java -Dserver.port=${PORT} -Xmx512m -jar api.jar
EXPOSE ${PORT}