FROM scottyengineering/java11
VOLUME /tmp
ARG JAR_FILE=./build/libs/petalk-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=prod", "/app.jar"]