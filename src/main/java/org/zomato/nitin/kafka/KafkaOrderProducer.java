package org.zomato.nitin.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.zomato.nitin.Model.Order;

@Service
public class KafkaOrderProducer {
    private String TOPIC = "zomato.orders";

    @Autowired
    private KafkaTemplate<String, Order> kafkaTemplate;

    private Logger logger = LoggerFactory.getLogger(KafkaOrderProducer.class);

    public void sendOrder(Order order) {
        this.kafkaTemplate.send(TOPIC, order);
        logger.info("Kafka :: Order Produced!");
    }

    @Bean
    public NewTopic createOrderTopic() {
        return new NewTopic(TOPIC, 3, (short) 1);
    }
}
