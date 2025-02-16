package org.zomato.nitin.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {


    private String TOPIC = "kafka.topic.customer";

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private Logger logger = LoggerFactory.getLogger(KafkaProducerService.class);

    public void sendMessage(String message) {
        this.kafkaTemplate.send(TOPIC, message);
        logger.info("Kafka Message Sent!");
    }

    @Bean
    public NewTopic createTopic(){
        return new NewTopic(TOPIC, 3,(short) 1);
    }
}
