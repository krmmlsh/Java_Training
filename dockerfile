FROM maven:3.5.2 as build
COPY . /usr/local/service
WORKDIR /usr/local/service
RUN mvn package

FROM tomcat:8.0-jre8
COPY --from=build /usr/local/service/rest/target/rest-2.0.0.war /usr/local/tomcat/webapps/app.war