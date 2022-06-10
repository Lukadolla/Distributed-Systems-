FROM openjdk:8-jre-alpine
#ENV SERVICE_HOST localhost
#ENV SERVICE_PORT 9000
COPY target/service-1.0-jar-with-dependencies.jar /service-1.0.jar
CMD ["/usr/bin/java", "-cp", "/service-1.0.jar", "quote.StockPrice"]