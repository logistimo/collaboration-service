#!/bin/bash -x

wget -P $HOME/ http://repo1.maven.org/maven2/co/elastic/apm/elastic-apm-agent/$APM_AGENT_VER/elastic-apm-agent-$APM_AGENT_VER.jar

exec java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom \
    -Dserver.port=$SERVER_PORT \
    -Dactivemq.broker-url=$ACTIVEMQ_URL \
    -Dspring.social.db.url=$MYSQL_SOCIAL_HOST \
    -Dspring.social.db.username=$MYSQL_SOCIAL_USER \
    -Dspring.social.db.password=$MYSQL_SOCIAL_PASS \
    -javaagent:$HOME/jmx_prometheus_javaagent-0.7.jar=$JAVA_AGENT_PORT:$HOME/jmx_exporter.json \
    -javaagent:$HOME/elastic-apm-agent-$APM_AGENT_VER.jar \
	-Delastic.apm.service_name=$SERVICE_NAME \
    -Delastic.apm.application_packages=com.logistimo.collaboration \
    -Delastic.apm.server_url=http://$APM_SERVER_URL \
    -jar $HOME/collaboration-service.jar