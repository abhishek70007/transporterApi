FROM adoptopenjdk/openjdk11:alpine-jre
EXPOSE 9090
VOLUME /tmp
ADD target/env.properties envDev.properties
ENTRYPOINT ["java","-jar","/TruckApi-0.0.1-SNAPSHOT.jar","--spring.config.import=envDev.properties"]