FROM openjdk:14
CMD ["mkdir", "app"]
WORKDIR app/
COPY target/algamoney-api-0.0.1-SNAPSHOT.jar algamoney-api/algamoney-api.jar
EXPOSE 8082
CMD ["java", "-jar", "algamoney-api/algamoney-api.jar"]