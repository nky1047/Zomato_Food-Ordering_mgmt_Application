package org.zomato.nitin.Services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zomato.nitin.Model.Restaurant;
import org.zomato.nitin.Repositories.RestaurantRepository;
import org.zomato.nitin.exceptions.RestaurantNotFoundExceptions;

import java.util.List;
import java.util.Optional;

@Service
public class RestaurantService {

    //@Autowired in Spring injects an instance of the RestaurantRepository in this attribute
    @Autowired
    private RestaurantRepository restaurantRepository;

    private static final Logger logger = LoggerFactory.getLogger(RestaurantService.class);

    public List<Restaurant> getAllRestaurants() {
        return restaurantRepository.findAll();
    }

    public Optional<Restaurant> getRestaurantById(String id) {
        return restaurantRepository.findById(id);
    }

    public Restaurant createRestaurant(Restaurant restaurant) {
        try {
            return restaurantRepository.save(restaurant);
        } catch (Exception e) {
            logger.error("Error occurred in Service Class while saving the restaurant", e);
            throw new RuntimeException("An error occurred while saving the restaurant", e);
        }
    }


    public Restaurant updateRestaurant(String restId, Restaurant updatedRestaurant) {
        try {
            Optional<Restaurant> existingRestaurantOpt = restaurantRepository.findById(restId);
            if (existingRestaurantOpt.isPresent()) {
                Restaurant existingRestaurant = existingRestaurantOpt.get();
                return restaurantRepository.save(existingRestaurant);
            } else {
                throw new RestaurantNotFoundExceptions("Restaurant with id: " + restId + "NOT FOUND !");
            }
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while updating the restaurant", e);
        }
    }

    public void deleteRestaurant(final String id) {
        restaurantRepository.deleteById(id);
    }

}
