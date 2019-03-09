FROM java:8-jre
MAINTAINER Pavel Homal <about.me/naxa>

ADD ./build/libs/turbine.jar /app/
CMD ["java", "-Xmx64m", "-jar", "/app/turbine.jar"]

EXPOSE 3000
