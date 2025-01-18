package org.zomato.nitin.Services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zomato.nitin.Model.Customer;
import org.zomato.nitin.Model.Order;
import org.zomato.nitin.Model.Restaurant;
import org.zomato.nitin.Repositories.CustomerRepository;
import org.zomato.nitin.Repositories.OrderRepository;
import org.zomato.nitin.Repositories.RestaurantRepository;
import org.zomato.nitin.validationUtil.ValidateOrderItems;

import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

@Service
public class OrderServiceImpl {
    @Autowired
    private OrderRepository orderRepo;

    @Autowired
    private CustomerRepository custRepo;

    @Autowired
    private CustomerServiceImpl customerService;

    @Autowired
    private RestaurantRepository restaurantRepo;

    @Autowired
    private RestaurantService restaurantService;

    private ValidateOrderItems validateOrderItems;

    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    public List<Order> getAllOrders() {
        return orderRepo.findAll();
    }

    public List<Order> getOrdersByRestaurantId(String restaurantId) {
        return orderRepo.findByRestaurantId(restaurantId);
    }

    public void createOrderToRestaurant(Order order) {
        try {
            Optional<Restaurant> restaurantOptional = restaurantRepo.findById(order.getRestaurantId());
            Optional<Customer> customerOptional = custRepo.findById(order.getCustomerId());

            if (restaurantOptional.isPresent() && validateOrderItems.compareMaps(restaurantOptional.get().getItemTable(),order.getOrderItems())) {
                if (customerOptional.isPresent()) {
                    Customer customer = customerOptional.get();
                    order.setStatus("PREPARING");
                    orderRepo.save(order);
                    customer.getMyOrdersList().add(order.getOrderId());
                    customerService.updateCustomer(customer.getCustomerId(),customer);
                } else {
                    logger.info("Invalid Customer!");
                    throw new RuntimeException("Customer Details did'nt match given in Order!");
                }
            } else {
                logger.info("Invalid Restaurant!");
                throw new RuntimeException("Restaurant Details did'nt match given in Order!");
            }
        } catch (Exception e) {
            logger.error("Error in creating your Order!!");
            throw new RuntimeException("An Exception Occured while creating Order!");
        }
    }

    public void updateOrderStatus(Order updatedOrder, String status) {
        Optional<Order> orderOptional = orderRepo.findById(updatedOrder.getOrderId());
        if (orderOptional.isPresent()) {
            Order latestOrder = updatedOrder;
            latestOrder.setStatus(status);
            latestOrder.setRating(updatedOrder.getRating());
            orderRepo.save(latestOrder);
        } else {
            throw new RuntimeException("Order not found!!");
        }
    }

}
