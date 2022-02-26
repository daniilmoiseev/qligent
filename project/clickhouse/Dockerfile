FROM openjdk:11-jdk
ADD ./build/libs/clickhouse-1.0-SNAPSHOT.jar /app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]