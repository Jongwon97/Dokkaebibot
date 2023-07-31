FROM openjdk:17-oracle
WORKDIR /back
COPY . .
RUN ./gradlew clean build
#ARG JAR_FILE=build/libs/core-0.0.1-SNAPSHOT.jar
#COPY ${JAR_FILE} backend.jar

ENTRYPOINT ["java", "-jar", "build/libs/Dokkaebi-0.0.1-SNAPSHOT.jar"]
