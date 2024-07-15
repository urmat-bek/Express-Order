package com.blg.express_order.dto;

public class ProductDTO {

    private String title;
    private String price;
    private String rating;
    private String productUrl;
    private String imageUrl;
    private String storeName;

    public ProductDTO() {
    }

    public ProductDTO(String title, String price, String productUrl, String imageUrl, String storeName) {
        this.title = title;
        this.price = price;
        this.productUrl = productUrl;
        this.imageUrl = imageUrl;
        this.storeName = storeName;

    }

    public ProductDTO(String title, String price, String rating, String productUrl, String imageUrl, String storeName) {
        this.title = title;
        this.price = price;
        this.rating = rating;
        this.productUrl = productUrl;
        this.imageUrl = imageUrl;
        this.storeName = storeName;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getProductUrl() {
        return productUrl;
    }

    public void setProductUrl(String productUrl) {
        this.productUrl = productUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }
}
