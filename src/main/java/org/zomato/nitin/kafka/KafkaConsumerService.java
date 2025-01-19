package org.zomato.nitin.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {

    @KafkaListener(topics = "${kafka.topic.customer}", groupId = "zomoto_demo_group")
    public void consumeCustomerMessage(String message) {
        // Process customer message
        System.out.println("Received customer message: " + message);
    }
}
