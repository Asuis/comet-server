FROM openjdk:8-jdk-alpine
MAINTAINER Asuis
VOLUME /var/docker/tmp
ADD conet-server-1.0.jar app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]