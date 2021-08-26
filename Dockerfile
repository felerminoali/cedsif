FROM openjdk:8
ADD target/cedsif.jar cedsif.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "cedsif.jar"]