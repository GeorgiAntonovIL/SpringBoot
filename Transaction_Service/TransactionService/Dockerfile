FROM openjdk:11

WORKDIR /project

COPY target/demo-0.0.1-SNAPSHOT.jar /project

CMD ["java","-jar","-Dspring.profiles.active=default","demo-0.0.1-SNAPSHOT.jar"]
