FROM adoptopenjdk/openjdk14:alpine-jre
ADD ./build/libs/ltt-0.1.jar /app.jar
CMD java -agentlib:jdwp=transport=dt_socket,address=*:5050,server=y,suspend=n -jar /build/libs/app.jar
# ENTRYPOINT ["java", "-jar", "app.jar"]
