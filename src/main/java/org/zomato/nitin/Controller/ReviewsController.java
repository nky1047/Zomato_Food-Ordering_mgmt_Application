package org.zomato.nitin.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.zomato.nitin.Model.Restaurant;
import org.zomato.nitin.Model.Review;
import org.zomato.nitin.Repositories.ReviewsRepository;
import org.zomato.nitin.Services.RestaurantService;
import org.zomato.nitin.Services.ReviewService;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
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

    //    GET REVIEWS BY RESTAURANT ID
    /*@GetMapping("/{restaurantId}/")
    public List<Review> getReviewsOfRestaurant(@PathVariable String restaurantId) {
        return reviewService.getReviewsByRestaurantId(restaurantId);
    }*/

//    ADD A REVEIEW FOR A RESTAURANT
    /*@PostMapping("/add/{restaurantId}/")
    public void addReviewtoRestaurant(@PathVariable String restaurantId, @RequestBody Review review) {
         reviewService.addReviewtoRestaurant(restaurantId, review);
    }*/

    @PostMapping("/new")
    public void addReviewtoRestaurant(@RequestBody Review review) {
        reviewService.createNewReview(review);
    }

    //    DELETE A REVIEW
    @DeleteMapping("/{reviewId}")
    public ResponseEntity<Void> deleteReview(@PathVariable final String reviewId) {
        reviewService.deleteReview(reviewId);
        return ResponseEntity.noContent().build();
    }

}
