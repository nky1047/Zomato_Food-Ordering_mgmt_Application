package org.zomato.nitin.Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mongodb.lang.NonNull;

import java.util.Objects;

public class Review {

    @JsonProperty("rating")
    private String rating;
    @JsonProperty("comment")
    private String comment;
    @JsonProperty("orderID")
    private String orderId;

    public Review() {                       /// THIS IS IMPORTANT TO CREATE
        super();
    }

    // Getters and Setters

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId= orderId;
    }

    // Constructor, toString(), equals(), hashCode()

    public Review( String rating, String comment, String orderId) {
        this.rating = rating;
        this.comment = comment;
        this.orderId = orderId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(rating, comment, orderId);
    }
}
