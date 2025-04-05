package org.zomato.nitin.Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mongodb.lang.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Hashtable;
import java.util.Objects;

@Document(collection = "order")
public class Order {
    @Id
    @Indexed(unique = true)
    @JsonProperty("orderId")
    private String orderId;
    @NonNull
    @JsonProperty("customerId")
    private String customerId;
    @NonNull
    @JsonProperty("restaurantId")
    private String restaurantId;
    @NonNull
    @JsonProperty("orderItems")
    Hashtable<String, String> orderItems;
    @NonNull
    @JsonProperty("orderValue")
    private String orderValue;
    @JsonProperty("status")
    private String status;
    @JsonProperty("review")
    private Review review;

    private Order(){
        super();
    }

    // Getters and Setters

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    @NonNull
    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(@NonNull String customerId) {
        this.customerId = customerId;
    }

    @NonNull
    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(@NonNull String restaurantId) {
        this.restaurantId = restaurantId;
    }

    @NonNull
    public Hashtable<String, String> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(@NonNull Hashtable<String, String> orderItems) {
        this.orderItems = orderItems;
    }

    @NonNull
    public String getOrderValue() {
        return orderValue;
    }

    public void setOrderValue(@NonNull String orderValue) {
        this.orderValue = orderValue;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Review getReview() {
        return review;
    }

    public void setReview(Review review) {
        this.review = review;
    }


    // Constructor, toString(), equals(), hashCode()


    public Order(String orderId, @NonNull String customerId, @NonNull String restaurantId, @NonNull Hashtable<String, String> orderItems, @NonNull String orderValue, String status, Review review) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.restaurantId = restaurantId;
        this.orderItems = orderItems;
        this.orderValue = orderValue;
        this.status = status;
        this.review = review;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId='" + orderId + '\'' +
                ", customerId='" + customerId + '\'' +
                ", restaurantId='" + restaurantId + '\'' +
                ", orderItems=" + orderItems +
                ", orderValue='" + orderValue + '\'' +
                ", status='" + status + '\'' +
                ", review=" + review +
                '}';
    }
}
