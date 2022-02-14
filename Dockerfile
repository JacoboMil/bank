FROM adoptopenjdk/openjdk11:jdk11u-nightly-slim

RUN addgroup --system docker
RUN adduser --system docker --ingroup docker

USER docker:docker

WORKDIR /app

COPY ./build/libs/*.jar ./app.jar

ENTRYPOINT [ "java", "-jar", "/app/app.jar" ]