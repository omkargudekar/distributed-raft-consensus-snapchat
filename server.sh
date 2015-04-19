#!/bin/bash
project_base="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

PROTOC_HOME=/usr/local/Cellar/protobuf/2.6.1/

if [ -d ${project_base}/src/com/distsc/comm/protobuf ]; then
  rm -r ${project_base}/src/com/distsc/comm/protobuf/*
fi

if [ -d ${project_base}/src/com/distsc/intercluster/proto ]; then
  rm -r ${project_base}/src/com/distsc/intercluster/proto/*
fi

$PROTOC_HOME/bin/protoc --proto_path=${project_base}/resources --java_out=${project_base}/src ${project_base}/resources/Message.proto

$PROTOC_HOME/bin/protoc --proto_path=${project_base}/resources --java_out=${project_base}/src ${project_base}/resources/app.proto