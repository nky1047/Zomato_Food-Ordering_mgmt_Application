package org.zomato.nitin.Services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zomato.nitin.Exceptions.PlaceOrderException;
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

import java.util.*;


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

    //    NEW REVIEW FOR ORDER
    public Order createNewReview(Review review) {
        Optional<Order> orderOptional = orderRepository.findById(review.getOrderId());
        Order currentOrder = orderOptional.get();
        if(currentOrder.getOrderId()==null){
            throw new PlaceOrderException("Invalid Order Id");
        }

        Order updatedOrder = orderOptional.get();
        if (orderOptional.isPresent()
               // && updatedOrder.getReview() != null
        ) {
            Review updatedReview = new Review();
            updatedReview.setComment(review.getComment());
            updatedReview.setRating(review.getRating());
            updatedOrder.setReview(updatedReview);
            updatedReview.setOrderId(updatedOrder.getOrderId());
            updatedOrder.setStatus("COMPLETED");
            orderService.updateOrderStatus(updatedOrder);
            logger.info("Rating :" + updatedOrder.getReview().getRating()
                    + ": given for Order ID:{}", updatedOrder.getOrderId());
            kafkaReviewProducerEg.sendReview(review);
            return updatedOrder;
        } else {
            logger.error("Error occurred in Service Class while saving Review::");
            throw new ReveiwsException("An error occurred while saving the Review::" + review);
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

    public Map<String, Object> getReviewsByRestaurantId(String restaurantId) {
        List<Order> orders = orderRepository.findOrdersByRestaurantIdWithReviews(restaurantId);
        Optional<Restaurant> restaurantOptional = restaurantRepo.findById(restaurantId);
        Restaurant restaurant = restaurantOptional.get();
        List<Review> reviews = new ArrayList<>();
        double reviewSum =0;
        int countReview = 0;
        for (Order order : orders) {
            if (order.getReview() != null) {
                reviews.add(order.getReview());
                reviewSum+=Double.parseDouble(order.getReview().getRating());
                countReview++;
            }
        }

        double averageReview = reviewSum/countReview;
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("Restaurant ID",restaurant.getRestaurantid());
        response.put("Restaurant Name",restaurant.getRestaurantName());
        response.put("Average Review:", averageReview);
        response.put("Reviews",reviews);

        return response;
    }
}