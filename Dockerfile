FROM openjdk:21-ea-oracle
ARG JAR_PATH=target/spring-web-39.jar
RUN mkdir /group39
WORKDIR /group39
COPY ${JAR_PATH} /group39
ENTRYPOINT ["java","-jar","spring-web-39.jar"]