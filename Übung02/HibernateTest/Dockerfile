FROM maven:3.5.2-jdk-8-alpine
COPY pom.xml /tmp/
COPY src /tmp/src/
WORKDIR /tmp/
RUN mvn package
# scheint zu gehen bis hier her aber cancer java mag meine Pfade nicht
CMD mvn exec:java
