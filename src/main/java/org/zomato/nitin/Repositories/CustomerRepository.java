package org.zomato.nitin.Repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.zomato.nitin.Model.Customer;

public interface CustomerRepository extends MongoRepository<Customer,String> {
    // Optional custom query methods
}
