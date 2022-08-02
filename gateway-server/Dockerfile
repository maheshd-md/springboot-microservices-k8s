FROM openjdk:8-jdk-alpine
MAINTAINER dhavalshankh.md@gmail.com
VOLUME /tmp
RUN addgroup -S "springboot-microservices-k8s"
RUN adduser -S maheshd -G "springboot-microservices-k8s"
ARG APP_NAME=gateway-server
RUN mkdir -p /home/maheshd
COPY target/${APP_NAME}-0.0.1-SNAPSHOT.jar /home/maheshd/app.jar
ENTRYPOINT ["java", "-jar", "/home/maheshd/app.jar"]
