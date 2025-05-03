package org.zomato.nitin.Utility;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.zomato.nitin.Model.Order;

public class PrettifyJsonUtility {

    static ObjectMapper objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);


//    public String prettyPrintUsingGlobalSetting(Order order) {
//
//        String prettyJson = null;
//        try {
//            prettyJson= objectMapper.writeValueAsString(order);
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException(e);
//        }
//        return prettyJson;
//    }

    public static String encodePrettily(Object obj) throws JsonProcessingException {

        String jsonString =null;
        try{
            jsonString = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return jsonString;
    }
}

//ObjectMapper mapper = new ObjectMapper();
//String alreadyProgressOrderJSON = null;
//            try {
//alreadyProgressOrderJSON = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(alreadyInProgressOrder);
//            } catch (JsonProcessingException e) {
//        throw new RuntimeException(e);
//            }


