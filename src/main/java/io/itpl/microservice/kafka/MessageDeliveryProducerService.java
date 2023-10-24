package io.itpl.microservice.kafka;

import io.itpl.microservice.pojo.MessageDelivery;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.Future;

@Service
@Slf4j
public class MessageDeliveryProducerService {

    @Autowired
    KafkaProducer<String, MessageDelivery> kafkaProducer;

    public void sendMessageToKafka(String topic, MessageDelivery message) {
        ProducerRecord<String, MessageDelivery> producerRecord = new ProducerRecord<>(topic, message);
        Future<RecordMetadata> metadataFuture = kafkaProducer.send(producerRecord);

        if (metadataFuture.isCancelled()) {
            log.info("Message delivery cancelled to kafka.");
        } else {
            log.info("Sending message to Kafka");
        }
    }
}
