FROM openjdk:8-jdk-alpine
VOLUME /tmp
EXPOSE 5000
ADD target/*.jar cameltest5-1.0.0-SNAPSHOT.jar
ENTRYPOINT [ "sh", "-c", "java -jar /cameltest5-1.0.0-SNAPSHOT.jar]