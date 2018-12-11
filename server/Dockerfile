FROM maven:3-jdk-8

ENV DB=NEO4J

COPY pom.xml /app/
WORKDIR /app/
COPY . /app/
RUN mvn clean compile -q
CMD mvn exec:java -Dexec.args="${DB}" -q
# CMD ["mvn", "exec:java"]