FROM maven:3.6-jdk-11

WORKDIR /microservice-duedate-mailer

COPY target/microservice-duedate-mailer-0.0.1-SNAPSHOT.jar /microservice-duedate-mailer/target/microservice-duedate-mailer-0.0.1-SNAPSHOT.jar

RUN ["ls", "/microservice-duedate-mailer"]

EXPOSE 6000

ENTRYPOINT [ "java", "-Xmx2048m", "-jar","/microservice-duedate-mailer/target/microservice-duedate-mailer-0.0.1-SNAPSHOT.jar" ]