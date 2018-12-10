FROM maven:3-jdk-8

ENV DB=OGM_MONGODB

COPY pom.xml /app/
WORKDIR /app/
COPY . /app/
RUN mvn clean compile
CMD mvn exec:java -Dexec.args="${DB}"
# CMD ["mvn", "exec:java"]