package com.blg.express_order.controller;

import com.blg.express_order.dto.ProductDTO;
import com.blg.express_order.entity.CartProducts;
import com.blg.express_order.entity.FavoriteProducts;
import com.blg.express_order.exceptions.CartProductsErrorResponse;
import com.blg.express_order.exceptions.CartProductsNotFoundException;
import com.blg.express_order.exceptions.FavoriteProductNotFoundException;
import com.blg.express_order.exceptions.FavoriteProductsErrorResponse;
import com.blg.express_order.facade.CartProductsFacade;
import com.blg.express_order.facade.FavoriteProductsFacade;
import com.blg.express_order.payload.response.MessageResponse;
import com.blg.express_order.service.FavoriteProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/favorites")
public class FavoriteProductsController {
    @Autowired
    private FavoriteProductsService favoriteProductsService;
    @Autowired
    private FavoriteProductsFacade productsFacade;

    @PostMapping("/add")
    public ResponseEntity<Object> addProduct(@RequestBody ProductDTO productDTO, Principal principal){
        FavoriteProducts favoriteProducts = favoriteProductsService.createFavoriteProducts(productDTO, principal);
        ProductDTO addedProduct = productsFacade.favoriteProductsToProductDTO(favoriteProducts);

        return new ResponseEntity<>(addedProduct, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ProductDTO>> getAllCartProductsForUser(Principal principal) {
        List<ProductDTO> postDTOList = favoriteProductsService.getAllFavoriteProductsForUser(principal)
                .stream()
                .map(productsFacade::favoriteProductsToProductDTO)
                .collect(Collectors.toList());

        return new ResponseEntity<>(postDTOList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getCartProductById(@PathVariable("id") Long id, Principal principal){
        FavoriteProducts favoriteProducts = favoriteProductsService.getFavoriteProductsById(id, principal);
        ProductDTO productDTO = productsFacade.favoriteProductsToProductDTO(favoriteProducts);

        return new ResponseEntity<>(productDTO, HttpStatus.OK);

    }


    @DeleteMapping("/{cartProductsId}/delete")
    public ResponseEntity<MessageResponse> deleteCartProducts(@PathVariable("cartProductsId") String cartProductsId, Principal principal) {
        favoriteProductsService.deleteFavoriteProducts(Long.parseLong(cartProductsId), principal);
        return new ResponseEntity<>(new MessageResponse("FavoriteProducts was deleted"), HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<FavoriteProductsErrorResponse> handleException(FavoriteProductNotFoundException e) {
        FavoriteProductsErrorResponse favoriteProductsErrorResponse = new FavoriteProductsErrorResponse(
                "FavoriteProducts with this id wasn't found!",
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(favoriteProductsErrorResponse, HttpStatus.NOT_FOUND);
    }
}
