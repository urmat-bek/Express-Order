package com.blg.express_order.facade;

import com.blg.express_order.dto.ProductDTO;
import com.blg.express_order.entity.FavoriteProducts;
import org.springframework.stereotype.Component;

@Component
public class FavoriteProductsFacade {

    public ProductDTO favoriteProductsToProductDTO(FavoriteProducts favoriteProducts){
        ProductDTO productDTO = new ProductDTO();

        productDTO.setId(favoriteProducts.getId());
        productDTO.setTitle(favoriteProducts.getTitle());
        productDTO.setPrice(favoriteProducts.getPrice());
        productDTO.setRating(favoriteProducts.getRating());
        productDTO.setProductUrl(favoriteProducts.getProductURL());
        productDTO.setImageUrl(favoriteProducts.getImageURL());
        productDTO.setStoreName(favoriteProducts.getStoreName());

        return productDTO;
    }
}
