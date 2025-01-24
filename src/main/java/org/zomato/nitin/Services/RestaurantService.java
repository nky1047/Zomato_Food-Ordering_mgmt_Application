package org.zomato.nitin.Services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zomato.nitin.Model.Restaurant;
import org.zomato.nitin.Repositories.RestaurantRepository;
import org.zomato.nitin.Exceptions.RestaurantExceptions;

import java.util.Hashtable;
import java.util.List;
import java.util.Optional;

@Service
public class RestaurantService {

    //@Autowired in Spring injects an instance of the RestaurantRepository in this attribute
    @Autowired
    private RestaurantRepository restaurantRepository;

    /*@Autowired
    private RestaurantService restaurantService;*/

    private static final Logger logger = LoggerFactory.getLogger(RestaurantService.class);

    public List<Restaurant> getAllRestaurants() {
        return restaurantRepository.findAll();
    }

    public Optional<Restaurant> getRestaurantById(String id) {
        return restaurantRepository.findById(id);
    }

    public Restaurant createRestaurant(Restaurant restaurant) {
        try {
            //return restaurantRepository.save(restaurant);
            if (restaurant.getRestaurantid() != null && restaurantRepository.existsById(restaurant.getRestaurantid())){
                    throw new RestaurantExceptions("Error creating new Restaurant, Restaurant Already Exists!");
            } else {
                logger.warn("Restaurant Created!!");
                return restaurantRepository.save(restaurant);
            }
        } catch (Exception e) {
            logger.error("Error occurred in Service Class while saving the restaurant", e);
            throw new RuntimeException("An error occurred while saving the restaurant", e);
        }
    }

    public Hashtable<String, String> getOrderItemsByRestaurantId(String restaurantId) {
        try {
            Optional<Restaurant> existingRestaurant = restaurantRepository.findById(restaurantId);
            if (existingRestaurant.isPresent()) {
                Restaurant restaurant = existingRestaurant.get();
                Hashtable<String, String> menu = restaurant.getItemTable();
                return menu;
            } else {
                throw new RestaurantExceptions("Restaurant with id: " + restaurantId + "NOT FOUND !");
            }
        } catch (Exception e) {
            throw new RuntimeException("An Internal Error occurred!", e);
        }
    }


    public Restaurant updateRestaurant(Restaurant updatedRestaurant) {
        try {
            Optional<Restaurant> RestaurantOpt = restaurantRepository.findById(updatedRestaurant.getRestaurantid());
            if (RestaurantOpt.isPresent()) {
                Restaurant newRestaurant = RestaurantOpt.get();
                newRestaurant.setRestaurantName(updatedRestaurant.getRestaurantName());
                newRestaurant.setLocation(updatedRestaurant.getLocation());
                newRestaurant.setItemTable(updatedRestaurant.getItemTable());
                newRestaurant.setCuisineType(updatedRestaurant.getCuisineType());
                return restaurantRepository.save(newRestaurant);
            } else {
                logger.error("Restaurant with id: " + updatedRestaurant.getRestaurantid() + "NOT FOUND !");
                throw new RestaurantExceptions("Restaurant with id: " + updatedRestaurant.getRestaurantid() + "NOT FOUND !");
            }
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while updating the restaurant", e);
        }
    }

    public void deleteRestaurant(String restaurantId) {
        restaurantRepository.deleteById(restaurantId);
    }

}
