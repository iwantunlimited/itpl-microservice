package io.itpl.microservice.kafka;

import io.itpl.microservice.common.UserObject;
import io.itpl.microservice.pojo.MessageDelivery;
import io.itpl.microservice.system.BroadcastMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.Future;

@Service
@Slf4j
public class KafkaProducerService {

    @Autowired
    KafkaProducer<String, MessageDelivery> messageDeliveryKafkaProducer;

    @Autowired
    KafkaProducer<String, BroadcastMessage> broadcastMessageBodyKafkaProducer;

    @Autowired
    KafkaProducer<String, UserObject> userObjectKafkaProducer;

    public void sendMessageDeliveryToKafka(String topic, String key, MessageDelivery message) {
        ProducerRecord<String, MessageDelivery> producerRecord = new ProducerRecord<>(topic, message);
        Future<RecordMetadata> metadataFuture = messageDeliveryKafkaProducer.send(producerRecord);

        if (metadataFuture.isCancelled()) {
            log.info("Message delivery cancelled to kafka.");
        } else {
            log.info("Sending MessageDelivery to Kafka");
        }
    }

    public void sendBroadCastMessageBodyToKafka(String topic, String key, BroadcastMessage message) {
        ProducerRecord<String, BroadcastMessage> producerRecord = new ProducerRecord<>(topic, message);
        Future<RecordMetadata> metadataFuture = broadcastMessageBodyKafkaProducer.send(producerRecord);

        if (metadataFuture.isCancelled()) {
            log.info("BroadCastMessageBody failed to send to kafka.");
        } else {
            log.info("Sending BroadCastMessageBody to Kafka");
        }
    }

    public void sendUserObjectToKafka(String topic, String key, UserObject userObject) {
        ProducerRecord<String, UserObject> producerRecord = new ProducerRecord<>(topic, userObject);
        Future<RecordMetadata> metadataFuture = userObjectKafkaProducer.send(producerRecord);

        if (metadataFuture.isCancelled()) {
            log.info("UserObject failed to send to kafka.");
        } else {
            log.info("Sending UserObject to Kafka");
        }
    }
}
