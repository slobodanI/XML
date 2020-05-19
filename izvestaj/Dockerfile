FROM openjdk:8-jdk-alpine
COPY entrypoint.sh /entrypoint.sh
COPY target/izvestaj-0.0.1-SNAPSHOT.jar izvestaj-0.0.1-SNAPSHOT.jar
RUN chmod +x /entrypoint.sh
CMD ["/entrypoint.sh"]
