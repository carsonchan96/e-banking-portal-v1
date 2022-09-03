FROM openjdk:17
ENV TZ=UTC
COPY target/e-banking-portal-0.0.1-SNAPSHOT.jar e-banking-portal-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/e-banking-portal-0.0.1-SNAPSHOT.jar"]