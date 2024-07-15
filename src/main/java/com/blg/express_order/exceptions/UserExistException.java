package com.blg.express_order.exceptions;

public class UserExistException extends RuntimeException{
    public UserExistException(String message) {
        super(message);
    }
}
