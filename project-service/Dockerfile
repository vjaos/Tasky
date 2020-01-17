FROM java:8-jre

ADD ./target/project-service.jar /app/
CMD ["java", "-Xmx200m", "-jar","/app/project-service.jar"]

EXPOSE 8087