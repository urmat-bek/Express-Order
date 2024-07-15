package com.blg.express_order.facade;

import com.blg.express_order.dto.ProductDTO;
import com.blg.express_order.entity.CartProducts;
import org.springframework.stereotype.Component;

@Component
public class CartProductsFacade {

    public ProductDTO cartProductsToProductDTO(CartProducts cartProducts){
        ProductDTO productDTO = new ProductDTO();

        productDTO.setId(cartProducts.getId());
        productDTO.setTitle(cartProducts.getTitle());
        productDTO.setPrice(cartProducts.getPrice());
        productDTO.setRating(cartProducts.getRating());
        productDTO.setProductUrl(cartProducts.getProductURL());
        productDTO.setImageUrl(cartProducts.getImageURL());
        productDTO.setStoreName(cartProducts.getStoreName());

        return productDTO;
    }
}
