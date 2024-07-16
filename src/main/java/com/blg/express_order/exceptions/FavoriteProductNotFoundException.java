package com.blg.express_order.exceptions;

public class FavoriteProductNotFoundException extends RuntimeException{
    public FavoriteProductNotFoundException(String msg){
        super(msg);
    }
}
