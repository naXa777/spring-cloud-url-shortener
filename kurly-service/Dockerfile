FROM openjdk:19-jdk-alpine
MAINTAINER Pavel Homal <about.me/naxa>

ENV PROFILE default

RUN apk --no-cache add curl
ADD ./build/libs/kurly-service.jar /app/
CMD ["java", "-Xmx64m", "-jar", "/app/kurly-service.jar", \
    "--spring.profiles.active=${PROFILE}"]

HEALTHCHECK --interval=30s --timeout=30s CMD curl -f http://localhost:9000/actuator/health || exit 1

EXPOSE 9000
