# Alpine Linux with OpenJDK JRE
FROM openjdk:8-jre-alpine
# copy fat WAR
COPY target/shopping-cart-0.0.1-SNAPSHOT.jar /app.war
# runs application
CMD ["/usr/bin/java", "-jar", "-Dspring.profiles.active=default", "/app.war"]