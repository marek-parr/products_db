FROM openjdk:8-jdk-alpine
VOLUME /tmp
EXPOSE 8080
COPY /build/libs/products_db-1.0-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar", "/app.jar"]