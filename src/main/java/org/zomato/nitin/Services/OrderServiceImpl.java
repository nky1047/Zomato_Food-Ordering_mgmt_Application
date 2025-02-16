package org.zomato.nitin.Services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.ErrorResponse;
import org.zomato.nitin.Exceptions.CustomerException;
import org.zomato.nitin.Exceptions.PlaceOrderException;
import org.zomato.nitin.Model.Customer;
import org.zomato.nitin.Model.Order;
import org.zomato.nitin.Model.Restaurant;
import org.zomato.nitin.Repositories.CustomerRepository;
import org.zomato.nitin.Repositories.OrderRepository;
import org.zomato.nitin.Repositories.RestaurantRepository;
import org.zomato.nitin.validationUtil.ValidateOrderItems;

import java.util.*;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static org.apache.logging.log4j.util.StringBuilders.equalsIgnoreCase;

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


    // KAFKA PRODUCER LOGIC FOR PLACING ORDER


    private final KafkaTemplate<String, Order> kafkaTemplate;
    //@Value("${kafka.topic.order}")
    private final String orderTopic = "kafka_topic_orders";


    public OrderServiceImpl(KafkaTemplate<String, Order> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }


    public List<Order> getAllOrders() {
        return orderRepo.findAll();
    }

    public List<Order> getOrdersByRestaurantId(String restaurantId) {
        return orderRepo.findByRestaurantId(restaurantId);
    }

    public Optional<Order> getOrderById(String orderId) {
        return orderRepo.findById(orderId);
    }

    public Order createOrderToRestaurant(Order order) {

        Optional<Restaurant> restaurantWithCurrentOrder = restaurantRepo.findById(order.getRestaurantId());
        Optional<Customer> customerWithCurrentOrder = custRepo.findById(order.getCustomerId());

        if (!customerWithCurrentOrder.isPresent()) {
            logger.info("Invalid Customer!");
            throw new PlaceOrderException("Invalid Customer ID!");
        }

        Order savedOrder = null;
        Boolean isValideOrderDetails = FALSE;
        Boolean isduplicateOrderByCustomerforRestaurant = FALSE;
        String duplicateOrderId = null;

        // CHECKING HERE IF CUSTOMER ALREADY HAS A ORDER ID THAT HAS STATUS "PREPARING"
        List<String> currentCustomerOrderList = customerWithCurrentOrder.get().getMyOrdersList();
        if (currentCustomerOrderList != null) {
            for (int i = 0; i < currentCustomerOrderList.size(); i++) {
                String tempCustistOrderId = currentCustomerOrderList.get(i);
                if (tempCustistOrderId != null) {
                    Order tempOrder = getOrderById(tempCustistOrderId).get();
                    if (tempOrder.getStatus().equals("PREPARING")
                            && tempOrder.getRestaurantId().equals(order.getRestaurantId()))
                        isduplicateOrderByCustomerforRestaurant = TRUE;
                }else
                    isduplicateOrderByCustomerforRestaurant = FALSE;
            }
        }
        if (isduplicateOrderByCustomerforRestaurant) {
            logger.info("Customer already has a Order being Prepared by Restaurant!");
            throw new PlaceOrderException("Dear Customer, An Order with is being Prepared by Requested Restaurant.");
        } else if (!restaurantWithCurrentOrder.isPresent()) {
            logger.info("Invalid Restaurant!");
            throw new PlaceOrderException("Invalid Restaurant ID!");
        } else if (!ValidateOrderItems.compareMaps(restaurantWithCurrentOrder.get().getItemTable(), order.getOrderItems())) {
            logger.info("Invalid Menu Items!");
            throw new PlaceOrderException("Ordered Menu Items not available!");
        } else isValideOrderDetails = TRUE;

        try {
            if (isValideOrderDetails && isduplicateOrderByCustomerforRestaurant == FALSE && restaurantWithCurrentOrder.isPresent() && ValidateOrderItems.compareMaps(restaurantWithCurrentOrder.get().getItemTable(), order.getOrderItems())

            ) {
                Customer customer = customerWithCurrentOrder.get();
                order.setStatus("PREPARING");
                savedOrder = orderRepo.save(order);
                logger.info("Order Created with ID:" + order.getOrderId());

                // Null Check for New Customer with null OrderList
                if (customer.getMyOrdersList() == null) {
                    customer.setMyOrdersList(Collections.singletonList(order.getOrderId()));
                logger.info("First Order of Customer :{}", customer.getCustomerName());
                } else {
                    customer.getMyOrdersList().add(order.getOrderId());
                }

//              kafkaTemplate.send(orderTopic, order.getOrderId(),order);       //// Send to Kafka Topic - Order with orderId as key
                customerService.updateCustomer(customer.getCustomerId(), customer);
            }
        } catch (Exception e) {
            logger.error("Some Other Error in creating your Order!!");
            throw new PlaceOrderException("An Exception Occured while creating Order!");

        }
        return savedOrder;
    }

    // @KafkaListener(topics = "kafka_topic_orders" , groupId = "order_group")
    public Order updateOrderStatus(Order updatedOrder) {
        Optional<Order> orderOptional = orderRepo.findById(updatedOrder.getOrderId());
        Optional<Restaurant> restaurantWithCurrentOrder = restaurantRepo.findById(updatedOrder.getRestaurantId());

        if (!ValidateOrderItems.compareMaps(restaurantWithCurrentOrder.get().getItemTable(), updatedOrder.getOrderItems())) {
            logger.info("Invalid Menu Items!");
            throw new PlaceOrderException("Ordered Menu Items not available!");
        }

        if (orderOptional.isPresent()
            //  && ValidateOrderItems.compareMaps(restaurantWithCurrentOrder.get().getItemTable(), updatedOrder.getOrderItems())
        ) {
            logger.info("UpdateOrder with Order ID: {}", updatedOrder.getOrderId());
            Order latestOrder = updatedOrder;
            //latestOrder.setStatus("Processing");
            //latestOrder.setRating(null);
            logger.info("Processing.. Order with ID: {}", latestOrder.getOrderId());
            orderRepo.save(latestOrder);
            return latestOrder;
        } else {
            throw new PlaceOrderException("Cannot Update Order!!");
        }
    }

}
