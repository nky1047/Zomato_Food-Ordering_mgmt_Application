package org.zomato.nitin.Controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.zomato.nitin.Model.Restaurant;
import org.zomato.nitin.Repositories.RestaurantRepository;
import org.zomato.nitin.Services.RestaurantService;
import org.zomato.nitin.Services.ReviewService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/restaurants")
public class RestaurantController {

    private static final Logger logger = LoggerFactory.getLogger(RestaurantController.class);

    @Autowired
    private RestaurantService restaurantService;
    private RestaurantRepository restaurantRepository;

    @Autowired
    private ReviewService reviewService;

    @GetMapping
    public ResponseEntity<List<Restaurant>> getAllRestaurants() {
        return ResponseEntity.ok(restaurantService.getAllRestaurants());
    }

    @GetMapping("/all")
    public ResponseEntity<List<Restaurant>> getAllRestaurants1() {
        return ResponseEntity.ok(restaurantService.getAllRestaurants());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Restaurant> getRestaurantById(@PathVariable String id) {
        Optional<Restaurant> restaurant = restaurantService.getRestaurantById(id);
        return restaurant.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Restaurant> createRestaurant(@RequestBody Restaurant restaurant) {
        try {
            return new ResponseEntity<>(restaurantService.createRestaurant(restaurant), HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Error occurred while creating the restaurant", e);
            throw new RuntimeException("An error occurred while creating the restaurant", e);
        }
    }

    @PutMapping("/{id}")
    public Restaurant updateRestaurant(@PathVariable String rstId, @RequestBody Restaurant restaurant) {
        return restaurantService.updateRestaurant(rstId, restaurant);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRestaurant(@PathVariable final String id) {
        restaurantService.deleteRestaurant(id);
        return ResponseEntity.noContent().build();
    }

    // Methods for handling reviews (add, update, delete)

}
