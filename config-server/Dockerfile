FROM openjdk:19-jdk-alpine
MAINTAINER Pavel Homal <about.me/naxa>

RUN apk --no-cache add curl
ADD ./build/libs/config-server.jar /app/
CMD ["java", "-Xmx64m", "-jar", "/app/config-server.jar"]

HEALTHCHECK --interval=30s --timeout=30s CMD curl -f http://localhost:8888/actuator/health || exit 1

EXPOSE 8888
