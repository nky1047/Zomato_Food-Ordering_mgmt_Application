package org.zomato.nitin.Services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zomato.nitin.Exceptions.ReveiwsException;
import org.zomato.nitin.Model.Customer;
import org.zomato.nitin.Model.Order;
import org.zomato.nitin.Model.Restaurant;
import org.zomato.nitin.Model.Review;
import org.zomato.nitin.Repositories.OrderRepository;
import org.zomato.nitin.Repositories.RestaurantRepository;
import org.zomato.nitin.Repositories.ReviewsRepository;
import org.zomato.nitin.kafka.Orders.KafkaOrderProducer;
import org.zomato.nitin.kafka.ReviewEx.KafkaReviewProducerEg;

import java.util.List;
import java.util.Optional;


@Service
public class ReviewService {
    @Autowired
    private ReviewsRepository reviewRepo;

    @Autowired
    private RestaurantRepository restaurantRepo;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderServiceImpl orderService;

    @Autowired
    private KafkaReviewProducerEg kafkaReviewProducerEg;                  //AutoWired Producer Class

    public static final Logger logger = LoggerFactory.getLogger(ReviewService.class);
//    GET ALL REVIEWS
    public List<Review> getAllReviews() {
        return reviewRepo.findAll();
    }
//    GET REVIEW BY ID
    public Optional<Review> getReviewById(String reviewId) {
        return reviewRepo.findById(reviewId);
    }
//    NEW REVIEW FOR ORDER
    public void createNewReview(Review review){
        try{
            Optional<Order> orderOptional= orderRepository.findById(review.getOrderId());
            if(orderOptional.isPresent()){
                reviewRepo.save(review);
                kafkaReviewProducerEg.sendReview(review);
                Order latestOrder = orderOptional.get();
                latestOrder.setRating(review.getRating());
                latestOrder.setStatus("COMPLETED");
                orderService.updateOrderStatus(latestOrder);
                logger.info("Rating :"+latestOrder.getRating()+": given for Order ID:{}",latestOrder.getOrderId());
            }
        }catch (ReveiwsException e){
            logger.error("Error occurred in Service Class while saving Review::", e);
            throw new ReveiwsException("An error occurred while saving the Review::"+review);
        }
    }
//    UPDATE REVIEW
    public Review updateReview(String reviewerId, String comment, String rating, Review review) {
        return reviewRepo.save(review);
    }
//    DELETE REVIEW
    public void deleteReview(final String reviewId) {
        reviewRepo.deleteById(reviewId);
    }
}