package org.zomato.nitin.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.zomato.nitin.Model.Order;
import org.zomato.nitin.Model.Review;
import org.zomato.nitin.Services.OrderServiceImpl;
import org.zomato.nitin.Services.RestaurantService;
import org.zomato.nitin.Services.ReviewService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ReviewsController {
    @Autowired
    private ReviewService reviewService;

    @Autowired
    private RestaurantService restaurantService;

    // Methods for handling reviews (add, update, delete)

    //    GET ALL REVIEWS
    @GetMapping
    public ResponseEntity<List<Review>> getAllReviews() {
        return ResponseEntity.ok(reviewService.getAllReviews());
    }

    @GetMapping("/restaurants/{restaurantId}/reviews")
    public ResponseEntity<Map<String, Object>> getReviewsByRestaurantId(@PathVariable String restaurantId) {
        Map<String,Object> response = reviewService.getReviewsByRestaurantId(restaurantId);
        return ResponseEntity.ok(response);
    }

//    ADD A REVEIEW FOR A RESTAURANT
    @PostMapping("reviews/new")
    public Order addReviewtoRestaurant(@RequestBody Review review) {
        Order order = new ResponseEntity<Order>(reviewService.createNewReview(review),HttpStatus.CREATED).getBody();
        return order;
    }

    //    DELETE A REVIEW
    @DeleteMapping("/{reviewId}")
    public ResponseEntity<Void> deleteReview(@PathVariable final String reviewId) {
        reviewService.deleteReview(reviewId);
        return ResponseEntity.noContent().build();
    }

}
