package org.zomato.nitin.Utility;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.zomato.nitin.Model.Order;

public class PretitfyJSON {
    public String prettyPrintUsingGlobalSetting(Order order) {
        ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
        String prettyJson = null;
        try {
            prettyJson= mapper.writeValueAsString(order);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return prettyJson;
    }
}
