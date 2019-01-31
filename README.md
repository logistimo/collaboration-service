# collaboration-service
Likes, comments and so on

## Pre-requisites
- Maven 3.2.3 or higher
- Git
- Jdk 1.8.0_112 or higher
- Mariadb 10.1.22
- Activemq 5.14.5 or higher
- Redis 2.8.14 or higher
- Docker

Build Instructions
------------------

To build the artifact and create a docker image of the collaboration service, run the following commands.

1. Set environment

```
export MAVEN_OPTS=-Xmx718m
export MAVEN_HOME=/opt/apache-maven-3.5.3/
export JAVA_HOME=/opt/java-home
export PATH=$JAVA_HOME/bin:$PATH:$MAVEN_HOME/bin
```
2. Build the artifact

```
mvn clean install
```
```
cd collaboration-application
```
3. Build the docker image

```
mvn docker:build
```