package org.zomato.nitin.Services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zomato.nitin.Exceptions.PlaceOrderException;
import org.zomato.nitin.Model.Customer;
import org.zomato.nitin.Model.Order;
import org.zomato.nitin.Model.Restaurant;
import org.zomato.nitin.Repositories.CustomerRepository;
import org.zomato.nitin.Repositories.OrderRepository;
import org.zomato.nitin.Repositories.RestaurantRepository;
import org.zomato.nitin.Utility.PrettifyJsonUtility;
import org.zomato.nitin.kafka.Orders.KafkaOrderProducer;
import org.zomato.nitin.kafka.KafkaProducerService;
import org.zomato.nitin.Utility.ValidateOrderItems;

import java.util.*;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

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

    @Autowired
    private KafkaProducerService kafkaProducerService;              //AutoWired Producer Class

    @Autowired
    private KafkaOrderProducer kafkaOrderProducer;                  //AutoWired Producer Class

    private ValidateOrderItems validateOrderItems;

    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);


    // KAFKA PRODUCER LOGIC FOR PLACING ORDER


    public List<Order> getAllOrders() {
        kafkaProducerService.sendMessage("ORDERS LIST FETCHED!");
        return orderRepo.findAll();
    }

    public Map<String, Object> getOrdersByRestaurantId(String restaurantId) {
        List<Order> orders = orderRepo.findOrdersByRestaurantIdWithReviews(restaurantId);
        Optional<Restaurant> restaurantOptional = restaurantRepo.findById(restaurantId);
        Restaurant restaurant = restaurantOptional.get();
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("Restaurant ID",restaurant.getRestaurantid());
        response.put("Restaurant Name",restaurant.getRestaurantName());
        response.put("Orders",orders);
        return response;
    }

    public Optional<Order> getOrderById(String orderId) {
       // kafkaProducerService.sendMessage("getOrderById used for Order ID:" + orderId);
        return orderRepo.findById(orderId);
    }

    public Order createOrderToRestaurant(Order order) {
        Optional<Restaurant> restaurantWithCurrentOrder = restaurantRepo.findById(order.getRestaurantId());
        Optional<Customer> customerWithCurrentOrder = custRepo.findById(order.getCustomerId());

//         CHECKING FOR INVALID CUSTOMER FIRST - then - further Validations
        if (!customerWithCurrentOrder.isPresent()) {
            logger.info("Invalid Customer!");
            throw new PlaceOrderException("Invalid Customer ID!");
        }

        Order savedOrder = null;                //THE ACTUAL ORDER TO BE SAVED POST VALIDATIONS
        Boolean isValideOrderDetails = FALSE;
        Boolean isduplicateOrderByCustomerforRestaurant = FALSE;
        Order alreadyInProgressOrder = null;    //THE Previous ORDER already in Progress

//         CHECKING HERE IF CUSTOMER ALREADY HAS A ORDER ID THAT HAS STATUS "PREPARING"
        List<String> currentCustomerOrderList = customerWithCurrentOrder.get().getMyOrdersList();

//        NUlL CHECK that Customer's Order List not null
        if (currentCustomerOrderList != null) {
            for (int i = 0; i < currentCustomerOrderList.size(); i++) {
                String tempCustistOrderId = currentCustomerOrderList.get(i);

//                NUlL CHECK that Customer's Order with the OrderId not null or Not Present in DB
                if (tempCustistOrderId != null) {


                    if (getOrderById(tempCustistOrderId) != null) {
                        Order tempOrder = getOrderById(tempCustistOrderId).get();

                        if (tempOrder.getStatus().equals("PREPARING") && tempOrder.getRestaurantId().equals(order.getRestaurantId())) {

                            isduplicateOrderByCustomerforRestaurant = TRUE;
                            alreadyInProgressOrder = tempOrder;
                        }
                    }
                } else isduplicateOrderByCustomerforRestaurant = FALSE;
            }
        }
        /*
         * If Previous Order with Customer Already In Progress - then -
         */
        if (isduplicateOrderByCustomerforRestaurant) {

            try{
                logger.info("Customer already has a Order being Prepared by Restaurant! with Order as below :\n" + PrettifyJsonUtility.encodePrettily(alreadyInProgressOrder));
                throw new PlaceOrderException("Dear Customer, An Order with is being Prepared by Requested Restaurant as below: " + PrettifyJsonUtility.encodePrettily(alreadyInProgressOrder));
            }
            catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }

        } else if (!restaurantWithCurrentOrder.isPresent()) {
            logger.info("Invalid Restaurant!");
            throw new PlaceOrderException("Invalid Restaurant ID!");
        } else if (!ValidateOrderItems.compareMaps(restaurantWithCurrentOrder.get().getItemTable(), order.getOrderItems())) {
            logger.info("Invalid Menu Items!");
            throw new PlaceOrderException("Ordered Menu Items not available!");
        }
        /*
         *  IF Above Validation Are False - then -
         */
        else isValideOrderDetails = TRUE;

        try {
            if (isValideOrderDetails && isduplicateOrderByCustomerforRestaurant == FALSE && restaurantWithCurrentOrder.isPresent() && ValidateOrderItems.compareMaps(restaurantWithCurrentOrder.get().getItemTable(), order.getOrderItems())

            ) {
                Customer customer = customerWithCurrentOrder.get();
                order.setStatus("PREPARING");
                savedOrder = orderRepo.save(order);
                logger.info("Order Created with ID:" + order.getOrderId());
                // KAFKA - MESSAGE SENT TO TOPIC
                kafkaProducerService.sendMessage("KAFKA:ORDER Created with ID: " + order.getOrderId());
                // KAFKA - ORDER SENT TO TOPIC
                kafkaOrderProducer.sendOrder(order);

                // Null Check for New Customer with null OrderList
                if (customer.getMyOrdersList() == null) {
                    customer.setMyOrdersList(Collections.singletonList(order.getOrderId()));
                    logger.info("First Order of Customer :{}", customer.getCustomerName());
                } else {
                    customer.getMyOrdersList().add(order.getOrderId());
                }
                customerService.updateCustomer(customer.getCustomerId(), customer);
            }
        } catch (Exception e) {
            logger.error("Some Other Error in creating your Order!!");
            throw new PlaceOrderException("An Exception Occured while creating Order!");

        }
        return savedOrder;
    }

    public Order updateOrderStatus(Order updatedOrder) {
        Optional<Order> orderOptional = orderRepo.findById(updatedOrder.getOrderId());
        Optional<Restaurant> restaurantWithCurrentOrder = restaurantRepo.findById(updatedOrder.getRestaurantId());
        if (!ValidateOrderItems.compareMaps(restaurantWithCurrentOrder.get().getItemTable(), updatedOrder.getOrderItems())) {
            logger.info("Invalid Menu Items!");
            throw new PlaceOrderException("Ordered Menu Items not available!");
        }

        if (orderOptional.isPresent()) {
            logger.info("UpdateOrder with Order ID: {}", updatedOrder.getOrderId());
            Order latestOrder = updatedOrder;
            logger.info("Processing.. Order with ID: {}", latestOrder.getOrderId());
            orderRepo.save(latestOrder);
            // KAFKA - Message Sent to Topic
            kafkaProducerService.sendMessage("Updated the Order with ID: " + latestOrder.getOrderId());
            return latestOrder;
        } else {
            throw new PlaceOrderException("Cannot Update Order!!");
        }
    }
}
