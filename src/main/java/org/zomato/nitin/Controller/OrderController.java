package org.zomato.nitin.Controller;

import org.apache.kafka.common.protocol.types.Field;
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

import java.util.*;

@RestController
@RequestMapping("/api/")
public class OrderController {
    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderServiceImpl orderService;

    @GetMapping("order")
    public ResponseEntity<List<Order>> getAllOrders(){return ResponseEntity.ok(orderService.getAllOrders());}

    @GetMapping("order/status/{orderId}")
    public Map<String,Object> getOrderById(@PathVariable String orderId){
        Optional<Order> orderOptional = orderService.getOrderById(orderId);
        Order order = orderOptional.get();
        Map<String,Object> response = new LinkedHashMap<>();
        response.put("OrderStatus",order.getOrderId());
        response.put("OrderStatus",order.getStatus());
        response.put("Items",order.getOrderItems());
        return ResponseEntity.ok(response).getBody();
    }

    /*
    * @RequestParam  - when in URL /orders/?restaurantId=gwje9gjwegoiwjowi/orders
    * @PathVariable  - when in URL /orders/gwje9gjwegoiwjowi/orders
    * */
    @GetMapping("order/{restaurantId}/orders")
    public ResponseEntity<Map<String, Object>> getOrderByRestaurant(@PathVariable String restaurantId){return ResponseEntity.ok(orderService.getOrdersByRestaurantId(restaurantId));}

    @PostMapping("order/new")
    public ResponseEntity<Order> newOrder(@RequestBody Order order){return new ResponseEntity<>(orderService.createOrderToRestaurant(order), HttpStatus.CREATED);}

    @PutMapping("order/update")
    public ResponseEntity<Order> processOrder(@RequestBody Order order){ return new ResponseEntity<>(orderService.updateOrderStatus(order),HttpStatus.ACCEPTED);}
}
