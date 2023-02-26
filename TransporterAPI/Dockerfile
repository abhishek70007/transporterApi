FROM adoptopenjdk/openjdk11:alpine-jre
EXPOSE 9090
VOLUME /tmp
ADD target/TransporterAPI-0.0.1-SNAPSHOT.jar TransporterAPI-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/TransporterAPI-0.0.1-SNAPSHOT.jar"]