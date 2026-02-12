FROM eclipse-temurin:17-jre-alpine
WORKDIR /user
COPY target/*.jar user.jar
EXPOSE 8090
ENTRYPOINT ["java","-jar","user.jar"]