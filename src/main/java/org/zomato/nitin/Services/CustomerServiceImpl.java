package org.zomato.nitin.Services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zomato.nitin.Exceptions.CustomerException;
import org.zomato.nitin.Model.Customer;
import org.zomato.nitin.Repositories.CustomerRepository;
import org.zomato.nitin.Exceptions.RestaurantExceptions;
import org.zomato.nitin.kafka.KafkaProducerService;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl {
    @Autowired
    private CustomerRepository custRepo;

   /* @Autowired
    private CustomerServiceImpl customerService;*/              //CANNOT USE SERVICE CLASS here as Circular Dependency Prohibited

    @Autowired
    private KafkaProducerService kafkaProducerService;

    //TO BE DONE
    private static final Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);

    public List<Customer> getAllCustomers() {
        kafkaProducerService.sendCustomerMessage("All Customers List Fetched!!");
        return custRepo.findAll();
    }

    public Optional<Customer> getCustomerById(String customerId) {
        try {
            return custRepo.findById(customerId);
        } catch (Exception e) {
            logger.error("Error occurred in Service Class while fetching the Customer", e);
            throw new RuntimeException("An error occurred while fetching the Customer", e);
        }
    }

    public Customer createNewCustomer(Customer customer) {
        try {

            //return custRepo.save(customer);
            if (custRepo.existsById(customer.getCustomerId())) {
                throw new CustomerException("Error creating new Customer, Customer Already Exists!");
            } else {
                kafkaProducerService.sendCustomerMessage("New customer created with Id:"+customer.getCustomerId());
                return custRepo.save(customer);
            }
        } catch (Exception e) {
            logger.error("Error occurred in Service Class while saving the Customer", e);
            throw new RuntimeException("An error occurred while saving the Customer", e);
        }
    }

    public Customer updateCustomer(String customerId, Customer updatedCustomer) {
        try {
            Optional<Customer> existingCustomer = custRepo.findById(customerId);
            if (existingCustomer.isPresent()) {
                Customer existingRestaurant = updatedCustomer;
                return custRepo.save(existingRestaurant);
            } else {
                throw new RestaurantExceptions("Customer with id: " + customerId + "NOT FOUND !");
            }
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while updating the Customer!", e);
        }
    }

}
