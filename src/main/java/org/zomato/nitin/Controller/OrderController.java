package org.zomato.nitin.Controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.zomato.nitin.Model.Order;
import org.zomato.nitin.Services.OrderServiceImpl;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/order")
public class OrderController {
    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderServiceImpl orderService;

    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders(){
        return ResponseEntity.ok(
                orderService.getAllOrders()
        );
    }

    @GetMapping("/status/{orderId}")
    public Order getOrderById(@PathVariable String orderId){
        Optional<Order> orderOptional = orderService.getOrderById(orderId);
        return orderOptional.get();
    }

    @GetMapping("/{restaurantId}")
    public List<Order> getOrderByRestaurant(@PathVariable String restaurantId){
        return orderService.getOrdersByRestaurantId(restaurantId);
    }

    @PostMapping("/new")
    public Order newOrder(@RequestBody Order order){
        try{
            orderService.createOrderToRestaurant(order);
        }catch (Exception e){
            logger.error("Error occurred while creating the Order in Controller", e);
            throw new RuntimeException("An error occurred while creating the Order in Controller", e);
        }
        return order;
    }
}
