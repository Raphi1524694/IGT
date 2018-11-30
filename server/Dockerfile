FROM maven:3.5.2-jdk-8-alpine

ARG DB
ARG PORT=6000

COPY pom.xml /tmp/
COPY src /tmp/src/
COPY builder/out/production/builder/Main.class /tmp/
WORKDIR /tmp/
EXPOSE $PORT
RUN java -cp . Main $DB && mvn package
CMD java -jar /tmp/target/IGT.jar $PORT
