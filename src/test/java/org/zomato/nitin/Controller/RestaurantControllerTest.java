package org.zomato.nitin.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.zomato.nitin.Model.Restaurant;
import org.zomato.nitin.Model.Review;
import org.zomato.nitin.Repositories.RestaurantRepository;
import org.zomato.nitin.Repositories.ReviewsRepository;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class RestaurantControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private ReviewsRepository reviewsRepository;

    @BeforeEach
    void setUp() {
    }

    @Test
    void getAllRestaurants() {
    }

    @Test
    void getRestaurantById() {
    }

    @Test
    void createRestaurant() throws Exception {
        Restaurant restaurant1 = new Restaurant();
        restaurant1.setRestaurantName("Hotel1");
        restaurant1.setLocation("Lucknow");
        restaurant1.setRestaurantid("f3g3t34t3");
        restaurant1.setCuisineType("North-Indian");
        List<String> menuItems = new ArrayList<>();
        menuItems.add("Kofta");
        menuItems.add("Paneer");
        menuItems.add("Biryani");
        restaurant1.setItemTable((Hashtable<String, String>) menuItems);
//        Review review1 = new Review("123", "4.5", "Excellent");
//        Review review2 = new Review("124", "4.5", "Excellent");
//        Review review3 = new Review("123", "4.5", "Excellent");
//        List<Objects> reviews1 = (List<Objects>) new Object();
//        reviews1.add(review1);
//        reviews1.add(review2);
//        reviews1.add(review3);
//        restaurant1.setReviews((List<Review>) reviews1);

        mockMvc.perform(post("/api/restaurants")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(restaurant1)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("The Good Place"))
                .andExpect(jsonPath("$.address").value("123 Main Street"))
                .andDo(print());
    }

    @Test
    void deleteRestaurant() {
    }
}