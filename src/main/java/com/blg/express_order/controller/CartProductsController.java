package com.blg.express_order.controller;

import com.blg.express_order.dto.ProductDTO;
import com.blg.express_order.entity.CartProducts;
import com.blg.express_order.exceptions.CartProductsErrorResponse;
import com.blg.express_order.exceptions.CartProductsNotFoundException;
import com.blg.express_order.facade.CartProductsFacade;
import com.blg.express_order.payload.response.MessageResponse;
import com.blg.express_order.service.CartProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

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

    @GetMapping("/all")
    public ResponseEntity<List<ProductDTO>> getAllCartProductsForUser(Principal principal) {
        List<ProductDTO> postDTOList = cartProductsService.getAllCartProductsForUser(principal)
                .stream()
                .map(productsFacade::cartProductsToProductDTO)
                .collect(Collectors.toList());

        return new ResponseEntity<>(postDTOList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getCartProductById(@PathVariable("id") Long id, Principal principal){
        CartProducts cartProducts = cartProductsService.getCartProductsById(id, principal);
        ProductDTO productDTO = productsFacade.cartProductsToProductDTO(cartProducts);

        return new ResponseEntity<>(productDTO, HttpStatus.OK);

    }


    @DeleteMapping("/{cartProductsId}/delete")
    public ResponseEntity<MessageResponse> deleteCartProducts(@PathVariable("cartProductsId") String cartProductsId, Principal principal) {
        cartProductsService.deleteCartProducts(Long.parseLong(cartProductsId), principal);
        return new ResponseEntity<>(new MessageResponse("CartProducts was deleted"), HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<CartProductsErrorResponse> handleException(CartProductsNotFoundException e) {
        CartProductsErrorResponse cartProductsErrorResponse = new CartProductsErrorResponse(
                "CartProducts with this id wasn't found!",
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(cartProductsErrorResponse, HttpStatus.NOT_FOUND);
    }
}
