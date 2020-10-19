FROM openjdk:11
ADD target/MovieAPI-0.0.1-SNAPSHOT.jar movie-api.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "movie-api.jar"]
