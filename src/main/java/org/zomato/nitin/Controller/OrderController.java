package org.zomato.nitin.Controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.aggregation.BooleanOperators;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.zomato.nitin.Exceptions.CustomerException;
import org.zomato.nitin.Model.Order;
import org.zomato.nitin.Services.OrderServiceImpl;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/")
public class OrderController {
    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderServiceImpl orderService;

    @GetMapping("order")
    public ResponseEntity<List<Order>> getAllOrders(){return ResponseEntity.ok(orderService.getAllOrders());}

    @GetMapping("order/status/{orderId}")
    public Order getOrderById(@PathVariable String orderId){
        Optional<Order> orderOptional = orderService.getOrderById(orderId);
        return orderOptional.get();
    }

    @GetMapping("order/{restaurantId}")
    public List<Order> getOrderByRestaurant(@PathVariable String restaurantId){
        return orderService.getOrdersByRestaurantId(restaurantId);
    }

    @PostMapping("order/new")
    public ResponseEntity<Order> newOrder(@RequestBody Order order){return new ResponseEntity<>(orderService.createOrderToRestaurant(order), HttpStatus.CREATED);}

    @PutMapping("order/update")
    public ResponseEntity<Order> processOrder(@RequestBody Order order){ return new ResponseEntity<>(orderService.updateOrderStatus(order),HttpStatus.ACCEPTED);}
}
