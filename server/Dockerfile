FROM maven:3.5.2-jdk-8-alpine

ARG DB
ENV PORT=6001

COPY ${DB}pom.xml /tmp/
COPY src /tmp/src/
COPY builder/out/production/builder/Main.class /tmp/
WORKDIR /tmp/
EXPOSE ${PORT}
LABEL db=${DB}
RUN java -cp . Main ${DB} && mvn package
CMD java -jar /tmp/target/IGT.jar $PORT
