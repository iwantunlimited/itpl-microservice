package io.itpl.microservice.kafka;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class KafkaURLConfiguration {
    @Value("${kafka-url}")
    private String kafkaURL;
}
