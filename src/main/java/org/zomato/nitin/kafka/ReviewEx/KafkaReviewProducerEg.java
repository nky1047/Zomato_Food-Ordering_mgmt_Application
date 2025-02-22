package org.zomato.nitin.kafka.ReviewEx;

import org.apache.kafka.clients.admin.NewTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.zomato.nitin.Model.Review;

@Service
public class KafkaReviewProducerEg {
    private String TOPIC = "zomato.reviews";

    @Autowired
    private KafkaTemplate<String, Review> kafkaTemplate;

    private Logger logger = LoggerFactory.getLogger(KafkaReviewProducerEg .class);

    public void sendReview(Review review) {
        this.kafkaTemplate.send(TOPIC, review);
        logger.info("Kafka :: Review Sent!");
    }

    @Bean
    public NewTopic createReviewTopic() {
        return new NewTopic(TOPIC, 3, (short) 1);
    }


}
