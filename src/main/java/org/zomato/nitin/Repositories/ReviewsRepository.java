package org.zomato.nitin.Repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.zomato.nitin.Model.Review;

import java.util.List;

public interface ReviewsRepository extends MongoRepository<Review, String> {

}
