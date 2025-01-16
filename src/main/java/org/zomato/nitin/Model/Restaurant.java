package org.zomato.nitin.Model;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Objects;

@Document(collection = "restaurants")
public class Restaurant {

    @Id
    private String id;
    @Indexed(unique = true)
    @JsonProperty("Name")
    private String name;
    @JsonProperty("Address")
    private String address;
    @JsonProperty("Cuisine")
    private String cuisine;
    @JsonProperty("Menu")
    private List<String> menuItems;
    @JsonProperty("Reviews")
    private List<Review> reviews;

    // Getters and Setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCuisine() {
        return cuisine;
    }

    public void setCuisine(String cuisine) {
        this.cuisine = cuisine;
    }

    public List<String> getMenuItems() {
        return menuItems;
    }

    public void setMenuItems(List<String> menuItems) {
        this.menuItems = menuItems;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }


    // Constructor, toString(), equals(), hashCode()



    public Restaurant(String id, String name, String address, String cuisine, List<String> menuItems, List<Review> reviews) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.cuisine = cuisine;
        this.menuItems = menuItems;
        this.reviews = reviews;
    }

    public Restaurant(){
        super();
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", cuisine='" + cuisine + '\'' +
                ", menuItems=" + menuItems +
                ", reviews=" + reviews +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Restaurant that = (Restaurant) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(address, that.address) && Objects.equals(cuisine, that.cuisine) && Objects.equals(menuItems, that.menuItems) && Objects.equals(reviews, that.reviews);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, address, cuisine, menuItems, reviews);
    }
}