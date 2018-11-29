FROM openjdk:8-jdk
EXPOSE 8080
COPY target/demo.jar /home
ENTRYPOINT ["java","-jar","/home/demo.jar"]

