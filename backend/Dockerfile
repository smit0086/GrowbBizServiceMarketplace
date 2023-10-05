FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
ARG JAR_FILE
COPY ./startup.sh .
COPY target/*.jar app.jar
# ENTRYPOINT ["sleep","5","java","-jar","/app.jar"]
CMD ["/bin/bash","-c","./startup.sh"]