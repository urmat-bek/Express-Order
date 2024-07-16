package com.blg.express_order.exceptions;

public class CartProductsNotFoundException extends RuntimeException{
    public CartProductsNotFoundException(String msg){
        super(msg);
    }
}
