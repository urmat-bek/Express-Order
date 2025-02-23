package com.blg.express_order.service;

import com.blg.express_order.dto.ProductDTO;
import com.blg.express_order.entity.CartProducts;

import com.blg.express_order.entity.User;
import com.blg.express_order.exceptions.CartProductsNotFoundException;
import com.blg.express_order.repository.CartProductsRepository;
import com.blg.express_order.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.List;

@Service
public class CartProductsService {

    public static final Logger LOG = LoggerFactory.getLogger(CartProductsService.class);
    private final CartProductsRepository cartProductsRepository;
    private final UserRepository userRepository;

    @Autowired
    public CartProductsService(CartProductsRepository cartProductsRepository, UserRepository userRepository) {
        this.cartProductsRepository = cartProductsRepository;
        this.userRepository = userRepository;
    }

    public CartProducts getCartProductsById(Long cartsProductId, Principal principal) {
        User user = getUserByPrincipal(principal);
        return cartProductsRepository.findCartProductsByIdAndUser(cartsProductId, user)
                .orElseThrow(() -> new CartProductsNotFoundException("CartProduct cannot be found for username: {}" + user.getEmail()));
    }

    public CartProducts createCartProducts(ProductDTO productDTO, Principal principal){
        User user = getUserByPrincipal(principal);
        CartProducts cartProducts = new CartProducts();

        cartProducts.setTitle(productDTO.getTitle());
        cartProducts.setPrice(productDTO.getPrice());
        cartProducts.setRating(productDTO.getRating());
        cartProducts.setProductURL(productDTO.getProductUrl());
        cartProducts.setImageURL(productDTO.getImageUrl());
        cartProducts.setStoreName(productDTO.getStoreName());
        cartProducts.setUser(user);

        LOG.info("Adding product in cart for user: {}" , user.getUsername() );
        return cartProductsRepository.save(cartProducts);
    }

    public List<CartProducts> getAllCartProductsForUser(Principal principal) {
        User user = getUserByPrincipal(principal);
        return cartProductsRepository.findAllByUser(user);
    }

    @Transactional
    public void deleteCartProducts(Long postId, Principal principal) {
        CartProducts cartProducts = getCartProductsById(postId, principal);
        cartProductsRepository.delete(cartProducts);

    }


    private User getUserByPrincipal(Principal principal) {
        String username = principal.getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found with username " + username));
    }


}
