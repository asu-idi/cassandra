FROM ubuntu:22.04

ARG TARGETPLATFORM
ARG DEBIAN_FRONTEND=noninteractive

RUN apt-get update && \
    apt-get install -y openjdk-8-jdk-headless wget build-essential git tmux python2 vim && \
    wget https://dlcdn.apache.org//ant/binaries/apache-ant-1.10.13-bin.tar.gz && \
    tar -xzf apache-ant-1.10.13-bin.tar.gz && \
    mv apache-ant-1.10.13 /opt/ant && \
    rm apache-ant-1.10.13-bin.tar.gz && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

COPY rocksandra rocksandra

ENV JAVA_HOME /usr/lib/jvm/java-8-openjdk-amd64
ENV ANT_HOME /opt/ant
ENV PATH $PATH:$JAVA_HOME/bin:$ANT_HOME/bin

WORKDIR /rocksandra

# ENTRYPOINT [ "/bin/bash" ]
CMD [ "/bin/bash" ]
