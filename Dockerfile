FROM maven:3.8.3-jdk-11-slim AS maven
WORKDIR /java
COPY . /java
RUN mvn clean package -DskipTests

FROM openjdk:11.0.10-jre-slim
WORKDIR /app
ENV MONGO_URI=mongodb://localhost:27017/api-mydrafts
ENV PROFILE=prod
ENV API_KEY=KEY
ENV SECRET_JWT=SECRET
ENV PORT=8080
COPY --from=maven /java/target/*.jar /app/api-mydrafts.jar
CMD java -Dserver.port=${PORT} -Xmx512m -jar api-mydrafts.jar
EXPOSE ${PORT}
