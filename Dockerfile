# Extend vert.x image
FROM vertx/vertx3

#                                                       
ENV VERTICLE_NAME com.cisco.blogapp.BlogAppVerticle
ENV VERTICLE_FILE target/blogapp-0.0.1-SNAPSHOT-fat.jar
ENV VERTICLE_CONFIG_FILE config.properties

# Set the location of the verticles
ENV VERTICLE_HOME /usr/verticles

EXPOSE 8090

# Copy your verticle to the container                   
COPY $VERTICLE_FILE $VERTICLE_HOME/
COPY $VERTICLE_CONFIG_FILE $VERTICLE_HOME/

# Launch the verticle
WORKDIR $VERTICLE_HOME
ENTRYPOINT ["sh", "-c"]
CMD ["vertx run $VERTICLE_NAME -cp $VERTICLE_HOME/*"]
