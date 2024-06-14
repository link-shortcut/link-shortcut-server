FROM amazoncorretto:17-alpine-jdk
EXPOSE 8080
COPY ./build/libs/*.jar app.jar
ENTRYPOINT ["java", "-Dspring.profiles.active=prod", "-jar", "/app.jar"]