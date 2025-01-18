package org.zomato.nitin.exceptions;

public class RestaurantNotFoundExceptions extends RuntimeException {
    public RestaurantNotFoundExceptions(String message) {
        super(message);
    }
}
