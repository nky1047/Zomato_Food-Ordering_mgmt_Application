package org.zomato.nitin.Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

@Document(collection = "reviews")
public class Review {

    @Id
    @JsonProperty("reviewId")
    private String reviewId;
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

    public String getReviewId() {
        return reviewId;
    }

    public void setReviewId(String reviewId) {
        this.reviewId = reviewId;
    }

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
        this.orderId = orderId;
    }


    // Constructor, toString(), equals(), hashCode()


    public Review(String reviewId, String rating, String comment, String orderId) {
        this.reviewId = reviewId;
        this.rating = rating;
        this.comment = comment;
        this.orderId = orderId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Review review = (Review) o;
        return Objects.equals(reviewId, review.reviewId) && Objects.equals(rating, review.rating) && Objects.equals(comment, review.comment) && Objects.equals(orderId, review.orderId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reviewId, rating, comment, orderId);
    }
}
