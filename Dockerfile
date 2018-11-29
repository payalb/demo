FROM openjdk:8-jdk
EXPOSE 8080
COPY target/demo.jar .
CMD ["java" "-jar" "demo.jar"]

