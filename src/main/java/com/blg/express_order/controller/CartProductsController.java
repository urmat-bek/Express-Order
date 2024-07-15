package com.blg.express_order.controller;

import com.blg.express_order.dto.ProductDTO;
import com.blg.express_order.entity.CartProducts;
import com.blg.express_order.facade.CartProductsFacade;
import com.blg.express_order.service.CartProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/cart")
public class CartProductsController {
    @Autowired
    private CartProductsService cartProductsService;
    @Autowired
    private CartProductsFacade productsFacade;

    @PostMapping("/add")
    public ResponseEntity<Object> addProduct(@RequestBody ProductDTO productDTO, Principal principal){
        CartProducts cartProducts = cartProductsService.createCartProducts(productDTO, principal);
        ProductDTO addedProduct = productsFacade.cartProductsToProductDTO(cartProducts);

        return new ResponseEntity<>(addedProduct, HttpStatus.OK);
    }
}
