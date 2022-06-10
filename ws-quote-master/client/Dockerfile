FROM openjdk:8-jre-alpine
ENV SERVICE_HOST localhost
ENV SERVICE_PORT 9000
COPY target/client-1.0-jar-with-dependencies.jar /client-1.0.jar
CMD /usr/bin/java -cp /client-1.0.jar quote.Client -h ${SERVICE_HOST} -p ${SERVICE_PORT}