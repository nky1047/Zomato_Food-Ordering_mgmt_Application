package org.zomato.nitin.Model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class Review {
    @JsonProperty("userId")
    private String userId;
    @JsonProperty("rating")
    private String rating;
    @JsonProperty("comment")
    private String comment;

    // Getters and Setters

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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


    // Constructor, toString(), equals(), hashCode()

    public Review(String userId, String rating, String comment) {
        this.userId = userId;
        this.rating = rating;
        this.comment = comment;
    }
    public Review(){
        super();
    }

    @Override
    public String toString() {
        return "Review{" + "userId='" + userId + '\'' + ", rating='" + rating + '\'' + ", comment='" + comment + '\'' + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Review review = (Review) o;
        return Objects.equals(userId, review.userId) && Objects.equals(rating, review.rating) && Objects.equals(comment, review.comment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, rating, comment);
    }
}
