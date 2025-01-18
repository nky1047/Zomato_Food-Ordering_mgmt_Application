package org.zomato.nitin.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.zomato.nitin.Model.Customer;
import org.zomato.nitin.Model.Restaurant;
import org.zomato.nitin.Model.Review;
import org.zomato.nitin.Repositories.CustomerRepository;
import org.zomato.nitin.Services.CustomerServiceImpl;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/customer")
public class CustomerContoller {
    @Autowired
    private CustomerServiceImpl custService;

    @GetMapping
    public ResponseEntity<List<Customer>> getAllCustomers() {
        return ResponseEntity.ok(custService.getAllCustomers());
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable String customerId){
        Optional<Customer> customerOptional = custService.getCustomerById(customerId);
        return customerOptional.map(ResponseEntity::ok).orElseGet(()-> ResponseEntity.notFound().build());
    }

    @PostMapping("/new")
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
        try {
            return new ResponseEntity<>(custService.createNewCustomer(customer), HttpStatus.CREATED);
        } catch (Exception e) {
           // logger.error("Error occurred while creating the restaurant", e);
            throw new RuntimeException("An error occurred while creating the restaurant", e);
        }
    }
}