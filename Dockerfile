FROM openjdk:8-jdk
EXPOSE 8080
COPY target/demo.jar /home
CMD ["java" "-jar" "/home/demo.jar"]

