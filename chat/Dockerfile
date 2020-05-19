FROM openjdk:8-jdk-alpine
COPY entrypoint.sh /entrypoint.sh
COPY target/chat-0.0.1-SNAPSHOT.jar chat-0.0.1-SNAPSHOT.jar
RUN chmod +x /entrypoint.sh
CMD ["/entrypoint.sh"]
