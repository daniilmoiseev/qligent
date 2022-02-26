#!/bin/bash

sleep 15.0s

kafkatopicsArrayString="$KAFKA_TOPICS"
IFS=' ' read -r -a kafkaTopicsArray <<< "$kafkatopicsArrayString"

zookeeperHostsValue=$ZOOKEEPER_HOSTS

for newTopic in "${kafkaTopicsArray[@]}"; do
  kafka-topics --create --topic "$newTopic" --partitions 1 --replication-factor 1 --if-not-exists --zookeeper "$zookeeperHostsValue"
done