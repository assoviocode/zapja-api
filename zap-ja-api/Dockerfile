FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
ARG JAR_FILE
COPY ${JAR_FILE} zapja-api.jar
ENTRYPOINT ["java","-jar","/zapja-api.jar"]
EXPOSE 8080