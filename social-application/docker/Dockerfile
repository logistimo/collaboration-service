FROM openjdk:8-jre

MAINTAINER dockers@logistimo.com


ENV HOME /root

ARG APP_NAME
ARG APP_VERSION

ADD ${APP_NAME}-${APP_VERSION}.jar $HOME/logistimo-social.jar

RUN bash -c 'touch $HOME/logistimo-social.jar'

ENV JAVA_OPTS="-Xms512M -Xmx1G" \
	SERVER_PORT=9010 \
	LOGI_URL=http://localhost:8080 \
	CALLISTO_URL=http://localhost:8090 \
	JAVA_AGENT_PORT=9099 \
	MSG_RESOURCE=messages_logi

RUN wget -P $HOME/ http://repo1.maven.org/maven2/io/prometheus/jmx/jmx_prometheus_javaagent/0.7/jmx_prometheus_javaagent-0.7.jar

ADD jmx_exporter.json $HOME/

COPY docker-entrypoint.sh /docker-entrypoint.sh

RUN chmod +x /docker-entrypoint.sh

EXPOSE 9010 9099

CMD ["/docker-entrypoint.sh"]
