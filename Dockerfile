FROM openjdk:11
WORKDIR /app
ADD target/todoProject-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-Dspring.profiles.active=prod","-jar","app.jar"]