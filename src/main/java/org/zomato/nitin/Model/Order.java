package org.zomato.nitin.Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Hashtable;
import java.util.Objects;

@Document(collection = "order")
public class Order {
    @Id
    @JsonProperty("orderId")
    private String orderId;
    @JsonProperty("customerId")
    private String customerId;
    @JsonProperty("restaurantId")
    private String restaurantId;
    @JsonProperty("orderItems")
    Hashtable<String, String> orderItems;
    @JsonProperty("orderValue")
    private String orderValue;
    @JsonProperty("rating")
    private String rating;
    @JsonProperty("status")
    private String status;

    // Getters and Setters

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public Hashtable<String, String> getOrderItems() {
        return orderItems;
    }

    public void setOderItems(Hashtable<String, String> orderItems) {
        this.orderItems = orderItems;
    }

    public String getOrderValue() {
        return orderValue;
    }

    public void setOderValue(String orderValue) {
        this.orderValue = orderValue;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    // Constructor, toString(), equals(), hashCode()


    public Order(String orderId, String customerId, String restaurantId, Hashtable<String, String> orderItems, String orderValue, String rating, String status) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.restaurantId = restaurantId;
        this.orderItems = orderItems;
        this.orderValue = orderValue;
        this.rating = rating;
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(orderId, order.orderId) && Objects.equals(customerId, order.customerId) && Objects.equals(restaurantId, order.restaurantId) && Objects.equals(orderItems, order.orderItems) && Objects.equals(orderValue, order.orderValue) && Objects.equals(rating, order.rating) && Objects.equals(status, order.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, customerId, restaurantId, orderItems, orderValue, rating, status);
    }
}
