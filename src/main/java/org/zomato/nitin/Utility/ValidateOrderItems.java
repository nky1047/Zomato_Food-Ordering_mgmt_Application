package org.zomato.nitin.Utility;

import java.util.Hashtable;
import java.util.Map;

public class ValidateOrderItems {
    public static boolean compareMaps(Hashtable<String, String> map2, Hashtable<String, String> map1) {
        for (Map.Entry<String, String> entry : map1.entrySet()) {                // Check if map2 contains all keys and values from map1
            if (!map2.containsKey(entry.getKey()) || !map2.get(entry.getKey()).equals(entry.getValue())) {  // Check if map2 contains the key and the corresponding value
                return false;                                   // If any key-value pair is missing or does not match, return false
            }
        }
        return true; // If all key-value pairs match, return true
    }
}
