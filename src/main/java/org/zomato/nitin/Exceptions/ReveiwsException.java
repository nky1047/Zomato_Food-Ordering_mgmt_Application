package org.zomato.nitin.Exceptions;

import org.apache.kafka.common.protocol.types.Field;

public class ReveiwsException extends RuntimeException {
    public ReveiwsException(String message) {
        super(message);
    }
}
