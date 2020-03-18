FROM openjdk:11
EXPOSE 8080
ADD target/corona-virus-tracker-0.0.1-SNAPSHOT.jar corona-virus-tracker-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/corona-virus-tracker-0.0.1-SNAPSHOT.jar"]
