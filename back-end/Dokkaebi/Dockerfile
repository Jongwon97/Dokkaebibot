FROM amazoncorretto:17
ARG JAR_FILE=build/libs/core-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} backend.jar
ENTRYPOINT ["java", "-jar", "backend.jar"]
