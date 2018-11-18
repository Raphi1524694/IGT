FROM maven:3.5.2-jdk-8-alpine
COPY pom.xml /tmp/
COPY src /tmp/src/
WORKDIR /tmp/
EXPOSE 6000
RUN mvn package
CMD java -jar /tmp/target/IGT.jar
