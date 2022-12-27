FROM openjdk:17-jdk-alpine
COPY ./ ./
RUN sh gradlew --no-daemon bootJar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "build/libs/matchingservice-0.0.1-SNAPSHOT.jar"]