FROM eclipse-temurin:21-jre
WORKDIR /app
COPY target/*.jar /app/front.jar
EXPOSE 8080
COPY entrypoint.sh /usr/local/bin/entrypoint.sh
RUN chmod +x /usr/local/bin/entrypoint.sh
# Run the entrypoint script
ENTRYPOINT ["/usr/local/bin/entrypoint.sh"]