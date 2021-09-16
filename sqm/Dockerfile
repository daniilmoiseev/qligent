FROM adoptopenjdk/openjdk14:alpine-jre
VOLUME /tmp

ADD ./build/libs/sqm-*.jar app.jar
RUN sh -c 'touch /app.jar'

EXPOSE 9092
EXPOSE 8080

ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -jar app.jar" ]