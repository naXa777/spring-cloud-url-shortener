FROM openjdk:19-jdk-alpine
MAINTAINER Pavel Homal <about.me/naxa>

ADD ./build/libs/hystrix-dashboard.jar /app/
CMD ["java", "-Xmx64m", "-jar", "/app/hystrix-dashboard.jar"]

EXPOSE 8080
