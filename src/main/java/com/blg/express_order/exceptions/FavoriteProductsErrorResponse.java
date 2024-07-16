package com.blg.express_order.exceptions;

public class FavoriteProductsErrorResponse {
    private String message;
    private Long time;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public FavoriteProductsErrorResponse(String message, Long time) {
        this.message = message;
        this.time = time;
    }
}
