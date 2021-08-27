FROM openjdk:8
ADD target/cedsif.jar cedsif.jar
EXPOSE 8086
ENTRYPOINT ["java", "-jar", "cedsif.jar"]