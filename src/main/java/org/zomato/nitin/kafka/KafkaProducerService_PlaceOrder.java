package org.zomato.nitin.kafka;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService_PlaceOrder {

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Value("${kafka.topic.customer}")
    private String customerTopic;

    public KafkaProducerService_PlaceOrder(KafkaTemplate<String,String> kafkaTemplate){
        this.kafkaTemplate=kafkaTemplate;
    }


    public void sendCustomerMessage(String message) {

        kafkaTemplate.send(customerTopic, message);
    }

}
