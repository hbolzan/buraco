FROM java:8-alpine
MAINTAINER Your Name <you@example.com>

ADD target/buraco-0.0.1-SNAPSHOT-standalone.jar /buraco/app.jar

EXPOSE 8080

CMD ["java", "-jar", "/buraco/app.jar"]
