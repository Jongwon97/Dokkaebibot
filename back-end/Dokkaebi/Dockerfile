FROM amazoncorretto:17-alpine
WORKDIR /back
COPY . .
RUN chmod +x gradlew
RUN ./gradlew clean build
#ARG JAR_FILE=build/libs/Dokkaebi-0.0.1-SNAPSHOT.jar
#COPY ${JAR_FILE} backend.jar

ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=prod", "build/libs/Dokkaebi-0.0.1-SNAPSHOT.jar"]
