package org.zomato.nitin.Repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.zomato.nitin.Model.Restaurant;

public interface RestaurantRepository extends MongoRepository<Restaurant, String> {
    // Optional custom query methods
}
