FROM eclipse-temurin:20-jre-alpine

VOLUME /tmp
COPY build/libs/*.jar app.jar
ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -jar /app.jar ${0} ${@}"]