FROM java:8-jre

ADD ./target/backend.jar /app/
CMD ["java", "-Xmx200m", "-jar","/app/backend.jar"]

EXPOSE 8088