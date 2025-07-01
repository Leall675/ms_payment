FROM openjdk:17-jdk-alpine
WORKDIR /payments
COPY target/*.jar /payments/payment.jar
COPY src/main/resources/application.properties /payments/application.properties
CMD ["java", "-jar", "/payments/payment.jar"]