FROM adoptopenjdk/openjdk14:alpine-jre
ADD ./build/libs/ltt-0.1.jar /app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
