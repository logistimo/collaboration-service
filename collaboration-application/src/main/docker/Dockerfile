FROM openjdk:8-jre

MAINTAINER dockers@logistimo.com


ENV HOME /root

ARG APP_NAME
ARG APP_VERSION

ADD ${APP_NAME}-${APP_VERSION}.jar $HOME/collaboration-service.jar

RUN bash -c 'touch $HOME/collaboration-service.jar'

ENV JAVA_OPTS="-Xms512M -Xmx1G" \
	SERVER_PORT=9070 \
	ACTIVEMQ_URL=failover:(tcp://localhost:61616)?randomize=false \
	MYSQL_SOCIAL_HOST=jdbc:mariadb://localhost:3306/social?useUnicode=true&amp;characterEncoding=UTF-8 \
    MYSQL_SOCIAL_USER=logistimo \
    MYSQL_SOCIAL_PASS=logistimo \
	JAVA_AGENT_PORT=9099

RUN wget -P $HOME/ https://repo1.maven.org/maven2/io/prometheus/jmx/jmx_prometheus_javaagent/0.7/jmx_prometheus_javaagent-0.7.jar

ADD jmx_exporter.json $HOME/

COPY docker-entrypoint.sh /docker-entrypoint.sh

RUN chmod +x /docker-entrypoint.sh

EXPOSE 9070 9099

CMD ["/docker-entrypoint.sh"]
