FROM adoptopenjdk/openjdk11
RUN addgroup spring && adduser  --ingroup spring --disabled-password spring
USER 10001
WORKDIR /home/spring/app
ARG JAR_FILE=build/libs/*.jar
COPY --chown=spring ${JAR_FILE} /home/spring/app/bin/tp-app-util-1.0.0.jar
ENTRYPOINT ["java","-Dapplication.name=util-app", "-javaagent:/home/spring/app/bin/opentelemetry-javaagent-all.jar", "-jar","/home/spring/app/bin/tp-app-util-1.0.0.jar"]

EXPOSE 8080