FROM eclipse-temurin:17-jre-alpine
COPY target/*.jar ecc.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/ecc.jar", "--spring.profiles.active=prod"]