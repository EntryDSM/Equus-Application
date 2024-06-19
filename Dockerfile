FROM openjdk:17
ENV TZ=Asia/Seoul
COPY /application-infrastructure/build/libs/application-infrastructure-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]
