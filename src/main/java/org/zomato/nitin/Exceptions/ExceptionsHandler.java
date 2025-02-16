package org.zomato.nitin.Exceptions;

import org.apache.kafka.common.protocol.types.Field;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ExceptionsHandler {

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(CustomerException.class)
    public Map<String,String> handleCustomerException(CustomerException customerException){
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("Error:",customerException.getMessage());
        return errorMap;
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(PlaceOrderException.class)
    public Map<String,String> HandlePlaceOrderException (PlaceOrderException placeOrderException){
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("Error:",placeOrderException.getMessage());
        return errorMap;
    }


    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(RestaurantExceptions.class)
    public Map<String,String> HandleRestaurantExceptions (RestaurantExceptions restaurantExceptions){
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("Error:",restaurantExceptions.getMessage());
        return errorMap;
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(ReveiwsException.class)
    public Map<String,String> HandleReveiwsExceptions (ReveiwsException reveiwsException){
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("Error:",reveiwsException.getMessage());
        return errorMap;
    }

}
