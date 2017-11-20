#!/bin/bash -x


exec java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom \
    -Dserver.port=$SERVER_PORT \
    -Dcallisto.url=$CALLISTO_URL \
    -Dlogistimo.url=$LOGI_URL \
    -Dmessage.resource=$MSG_RESOURCE \
    -javaagent:$HOME/jmx_prometheus_javaagent-0.7.jar=$JAVA_AGENT_PORT:$HOME/jmx_exporter.json \
    -jar $HOME/event-summarizer.jar
