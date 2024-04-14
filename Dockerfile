FROM openjdk:17-alpine

ARG JAR_FILE=/build/libs/book_service-0.0.1-SNAPSHOT.jar

COPY ${JAR_FILE} /book_service.jar

ENTRYPOINT ["java","-jar","-Dspring.profiles.active=prod", "/book_service.jar"]