FROM openjdk:17
ENV TZ=Asia/Seoul
COPY build/libs/Equus-Application-0.0.1-SNAPSHOT app.jar
COPY /datadog/dd-java-agent.jar /usr/agent/dd-java-agent.jar

ARG CLOUD_CONFIG_IMPORT_URL
ENV CLOUD_CONFIG_IMPORT_URL=$CLOUD_CONFIG_IMPORT_URL

ENTRYPOINT ["java","-javaagent:/usr/agent/dd-java-agent.jar", "-Ddd.agent.host=localhost", "-Ddd.profiling.enabled=true","-XX:FlightRecorderOptions=stackdepth=256", "-Ddd.logs.injection=true", "-Ddd.service=discovery-api", "-Ddd.env=prod", "-Dspring.profiles.active=production", "-jar", "/app.jar"]
