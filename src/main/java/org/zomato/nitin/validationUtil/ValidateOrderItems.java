package org.zomato.nitin.validationUtil;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

public class ValidateOrderItems {
    public static boolean compareMaps(Hashtable<String, String> map1, Hashtable<String, String> map2) {
        // Check if map2 contains all keys and values from map1
        for (Map.Entry<String, String> entry : map1.entrySet()) {
            // Check if map2 contains the key and the corresponding value
            if (!map2.containsKey(entry.getKey()) || !map2.get(entry.getKey()).equals(entry.getValue())) {
                return false; // If any key-value pair is missing or does not match, return false
            }
        }
        return true; // If all key-value pairs match, return true
    }
}
