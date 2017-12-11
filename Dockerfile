FROM gradle:4.2.1-jdk8-alpine as builder
USER root
COPY . .
ARG apiVersion
RUN gradle --no-daemon -PapiVersion=${apiVersion} build

FROM openjdk:8-jre-alpine
COPY --from=builder /home/gradle/build/deps/*.jar /data/
COPY --from=builder /home/gradle/build/libs/fint-consumer-felles-kodeverk-*.jar /data/fint-consumer-felles-kodeverk.jar
CMD ["java", "-jar", "/data/fint-consumer-felles-kodeverk.jar"]
