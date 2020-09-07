Spring Boot Graylog

The following commands will download all the required Docker images and start a container for each service:

$ docker run --name mongo -d mongo:3
$ docker run --name elasticsearch -p 9200:9200 -p 9300:9300 \
    -e ES_JAVA_OPTS="-Xms2g -Xmx4g" \
    -e "discovery.type=single-node" -e "xpack.security.enabled=false" \
    -e "bootstrap.memory_lock=true" --ulimit memlock=-1:-1 \
    -d docker.elastic.co/elasticsearch/elasticsearch:5.6.11
$ docker run --name graylog --link mongo --link elasticsearch \
    -p 9000:9000 -p 12201:12201 -p 514:514 -p 5555:5555 \
    -e GRAYLOG_WEB_ENDPOINT_URI="http://127.0.0.1:9000/api" \
    -d graylog/graylog:2.4.6-1

