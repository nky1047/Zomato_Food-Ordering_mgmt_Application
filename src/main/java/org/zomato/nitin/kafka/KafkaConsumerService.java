package org.zomato.nitin.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.zomato.nitin.Model.Order;

@Service
public class KafkaConsumerService {

    @KafkaListener(topics = "kafka.topic.customer", groupId = "order_group")
    public void consumeCustomerMessage(String message) {
//         Process customer message
        System.out.println("Message Received from Kafka: " + message );
    }


//    @KafkaListener(topics = "zomato.orders", groupId = "order_group_id")
//    public void consumerOrder(Order order){
//        System.out.println("Order Received from Kafka: " + order.getOrderId() );
//    }
}
