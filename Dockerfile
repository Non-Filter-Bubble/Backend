FROM openjdk:17-alpine

ARG JAR_FILE=/build/libs/BookService-0.0.1-SNAPSHOT.jar

COPY ${JAR_FILE} /BookService.jar

ENTRYPOINT ["java","-jar","-Dspring.profiles.active=prod", "/BookService.jar"]