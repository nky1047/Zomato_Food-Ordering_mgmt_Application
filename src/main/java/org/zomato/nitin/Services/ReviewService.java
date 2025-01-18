package org.zomato.nitin.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zomato.nitin.Model.Customer;
import org.zomato.nitin.Model.Restaurant;
import org.zomato.nitin.Model.Review;
import org.zomato.nitin.Repositories.RestaurantRepository;
import org.zomato.nitin.Repositories.ReviewsRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {
    @Autowired
    private ReviewsRepository reviewRepo;

    @Autowired
    private RestaurantRepository restaurantRepo;

    public List<Review> getAllReviews() {
        return reviewRepo.findAll();
    }

    public Optional<Review> getReviewByreviewerId(String reviewerId) {
        return reviewRepo.findById(reviewerId);
    }

   /* public List<Review> getReviewsByRestaurantId(String restaurantId) {
        return reviewRepo.findByRestaurantId(restaurantId);
    }*/

    public Review createNewReview(Review review){
        try{
            return reviewRepo.save(review);
        }catch (Exception e){
//            logger.error("Error occurred in Service Class while saving the Customer", e);
            throw new RuntimeException("An error occurred while saving the Customer", e);
        }
    }
    /*public void addReviewtoRestaurant(String restaurantId, Review review) {
        Optional<Restaurant> restaurantOpt = restaurantRepo.findById(restaurantId);
        if (restaurantOpt.isPresent()) {
            review.(restaurantId);
            reviewRepo.save(review);                            //// Save the review with the linked restaurantID
        } else {
            throw new RuntimeException("Restaurant not found");
        }
    }*/

    public Review updateReview(String reviewerId, String comment, String rating, Review review) {
        return reviewRepo.save(review);
    }

    public void deleteReview(final String reviewerId) {
        reviewRepo.deleteById(reviewerId);
    }
}
