package org.zomato.nitin.Services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zomato.nitin.Model.Customer;
import org.zomato.nitin.Repositories.CustomerRepository;

import java.util.List;

@Service
public class CustomerServiceImpl {
    @Autowired
    private CustomerRepository custRepo;

    //TO BE DONE
    private static final Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);

    public List<Customer> getAllCustomers(){
        return custRepo.findAll();
    }

    public Customer createNewCustomer(Customer customer){
        try{
            return custRepo.save(customer);
        }catch (Exception e){
            logger.error("Error occurred in Service Class while saving the Customer", e);
            throw new RuntimeException("An error occurred while saving the Customer", e);
        }
    }

}
