#!/bin/bash -x


exec java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom \
    -Dserver.port=$SERVER_PORT \
    -Dactivemq.broker-url=$ACTIVEMQ_URL \
    -Dspring.social.db.url=$MYSQL_SOCIAL_HOST \
    -Dspring.social.db.username=$MYSQL_SOCIAL_USER \
    -Dspring.social.db.password=$MYSQL_SOCIAL_PASS \
    -javaagent:$HOME/jmx_prometheus_javaagent-0.7.jar=$JAVA_AGENT_PORT:$HOME/jmx_exporter.json \
    -jar $HOME/collaboration-service.jar