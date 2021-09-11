FROM maven:3.6.3-jdk-11-slim AS build

ENV BASE_DIR=/app

RUN mkdir $BASE_DIR
WORKDIR $BASE_DIR
ADD ./pom.xml $BASE_DIR

ADD ./src/ $BASE_DIR/src

RUN mvn package -DskipTests

FROM adoptopenjdk/openjdk11:jre-11.0.9.1_1-alpine

ENV BASE_DIR=/app

RUN mkdir $BASE_DIR
WORKDIR $BASE_DIR
COPY --from=build $BASE_DIR/target/library.jar $BASE_DIR/

EXPOSE 8080
VOLUME /logs/

ENTRYPOINT ["java", "-jar", "/app/library.jar"]
