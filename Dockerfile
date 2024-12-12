FROM eclipse-temurin:21-jre
WORKDIR /app
COPY target/bookstore-front-0.0.1-SNAPSHOT.jar /app/front.jar
COPY entrypoint.sh /usr/local/bin/entrypoint.sh
RUN chmod +x /usr/local/bin/entrypoint.sh
# Run the entrypoint script
ENTRYPOINT ["/usr/local/bin/entrypoint.sh"]