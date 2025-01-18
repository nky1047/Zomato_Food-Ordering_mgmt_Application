package org.zomato.nitin.Repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.zomato.nitin.Model.Order;
import org.zomato.nitin.Model.Restaurant;

import java.util.List;

public interface OrderRepository extends MongoRepository<Order, String> {

    // Optional custom query methods
    List<Order> findByRestaurantId(String restaurantId);
}
