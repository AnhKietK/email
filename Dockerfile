# Giai đoạn 1: build file WAR
FROM maven:3-eclipse-temurin-25 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn -q clean package -DskipTests

# Giai đoạn 2: chạy bằng Tomcat 10.1
FROM tomcat:10.1-jdk25-temurin
RUN rm -rf /usr/local/tomcat/webapps/*
COPY --from=build /app/target/email.war /usr/local/tomcat/webapps/ROOT.war
EXPOSE 8080