package org.zomato.nitin.Model;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Hashtable;
import java.util.List;
import java.util.Objects;

@Document(collection = "restaurants")
public class Restaurant {

    @Id
    @JsonProperty("restaurantId")
    private String restaurantid;
    @JsonProperty("restaurantName")
    private String restaurantName;
    @JsonProperty("location")
    private String location;
    @JsonProperty("cuisineType")
    private String cuisineType;
    @JsonProperty("menu")
    Hashtable<String, String> itemTable;

//    @DBRef                                    //To link two POJOs with the same ID in a Spring Boot project using MongoDB, you can leverage Spring Data MongoDB’s @DBRef annotation
//    private List<Review> reviews;

    public Restaurant(){
        super();
    }

    // Getters and Setters

    public String getRestaurantid() {
        return restaurantid;
    }

    public void setRestaurantid(String restaurantid) {
        this.restaurantid = restaurantid;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCuisineType() {
        return cuisineType;
    }

    public void setCuisineType(String cuisineType) {
        this.cuisineType = cuisineType;
    }

    public Hashtable<String, String> getItemTable() {
        return itemTable;
    }

    public void setItemTable(Hashtable<String, String> itemTable) {
        this.itemTable = itemTable;
    }


    // Constructor, toString(), equals(), hashCode()


    public Restaurant(String restaurantid, String restaurantName, String location, String cuisineType, Hashtable<String, String> itemTable) {
        this.restaurantid = restaurantid;
        this.restaurantName = restaurantName;
        this.location = location;
        this.cuisineType = cuisineType;
        this.itemTable = itemTable;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Restaurant that = (Restaurant) o;
        return Objects.equals(restaurantid, that.restaurantid) && Objects.equals(restaurantName, that.restaurantName) && Objects.equals(location, that.location) && Objects.equals(cuisineType, that.cuisineType) && Objects.equals(itemTable, that.itemTable);
    }

    @Override
    public int hashCode() {
        return Objects.hash(restaurantid, restaurantName, location, cuisineType, itemTable);
    }
}