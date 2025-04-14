FROM openjdk:22-jdk-slim
EXPOSE 8082
ADD target/spring-boot-docker-zomato.jar spring-boot-docker-zomato.jar
ENTRYPOINT ["java", "-jar", "spring-boot-docker-zomato.jar"]
