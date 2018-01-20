FROM alpine:3.7
RUN apk --update add openjdk8-jre
ADD target/orderservice.jar /opt/service/orderservice.jar
EXPOSE 8081

CMD java -jar /opt/service/orderservice.jar
