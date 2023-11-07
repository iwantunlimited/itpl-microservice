package io.itpl.microservice.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.*;
import org.apache.kafka.common.config.TopicConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ExecutionException;

@Configuration
@Slf4j
public class TopicConfiguration {

    public final static String MESSAGE_DELIVERY_TOPIC = "MESSAGE_DELIVERY";
    public final static String BROADCAST_TOPIC = "BROADCAST";
    public final static String USEROBJECT_TOPIC = "USEROBJECT";
    @Autowired
    KafkaURLConfiguration kafkaURLConfiguration;

    @Bean
    public KafkaAdmin admin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaURLConfiguration.getKafkaURL());
        return new KafkaAdmin(configs);
    }

    @Bean(MESSAGE_DELIVERY_TOPIC)
    public NewTopic messageDelivery() throws ExecutionException, InterruptedException {
        if (doesTopicExist(MESSAGE_DELIVERY_TOPIC)) {
            log.info("Topic already exists : {}", MESSAGE_DELIVERY_TOPIC);
            return null;
        }
        return TopicBuilder.name(MESSAGE_DELIVERY_TOPIC).partitions(10).config(TopicConfig.RETENTION_MS_CONFIG, "3600000") // minutes * 60,000
                .config(TopicConfig.CLEANUP_POLICY_CONFIG, TopicConfig.CLEANUP_POLICY_DELETE).config(TopicConfig.SEGMENT_MS_CONFIG, "3600000") // minutes * 60,000
                .build();
    }

    @Bean(BROADCAST_TOPIC)
    public NewTopic broadCast() throws ExecutionException, InterruptedException {
        if (doesTopicExist(BROADCAST_TOPIC)) {
            log.info("Topic already exists : {}", BROADCAST_TOPIC);
            return null;
        }
        return TopicBuilder.name(BROADCAST_TOPIC).partitions(10).config(TopicConfig.RETENTION_MS_CONFIG, "1800000") // minutes * 60,000
                .config(TopicConfig.CLEANUP_POLICY_CONFIG, TopicConfig.CLEANUP_POLICY_DELETE).config(TopicConfig.SEGMENT_MS_CONFIG, "1800000") // minutes * 60,000
                .build();
    }

    @Bean(USEROBJECT_TOPIC)
    public NewTopic userObject() throws ExecutionException, InterruptedException {
        if (doesTopicExist(USEROBJECT_TOPIC)) {
            log.info("Topic already exists : {}", USEROBJECT_TOPIC);
            return null;
        }
        return TopicBuilder.name(USEROBJECT_TOPIC).partitions(10).config(TopicConfig.RETENTION_MS_CONFIG, "3600000") // minutes * 60,000
                .config(TopicConfig.CLEANUP_POLICY_CONFIG, TopicConfig.CLEANUP_POLICY_DELETE).config(TopicConfig.SEGMENT_MS_CONFIG, "3600000") // minutes * 60,000
                .build();
    }

    private boolean doesTopicExist(String topic) throws ExecutionException, InterruptedException {
        Properties properties = new Properties();
        properties.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaURLConfiguration.getKafkaURL());

        try (AdminClient adminClient = AdminClient.create(properties)) {
            ListTopicsResult topicsResult = adminClient.listTopics(new ListTopicsOptions().listInternal(true));
            Set<String> topicNames = topicsResult.names().get();
            return topicNames.contains(topic);
        }
    }
}
