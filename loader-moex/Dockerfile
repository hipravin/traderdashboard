FROM openjdk:17-alpine
ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} app.jar
#RUN java -jar /app.jar --spring.profiles.active=dockerliveload >/moex-load.log 2>&1

# copy crontabs for root user
COPY config/cronjobs /etc/crontabs/root

# start crond with log level 8 in foreground, output to stderr
CMD ["crond", "-f", "-d", "8"]