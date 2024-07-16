package com.blg.express_order.service;

import com.blg.express_order.dto.ProductDTO;
import com.blg.express_order.entity.FavoriteProducts;
import com.blg.express_order.entity.User;
import com.blg.express_order.exceptions.FavoriteProductNotFoundException;
import com.blg.express_order.repository.FavoriteProductsRepository;
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
public class FavoriteProductsService {

    public static final Logger LOG = LoggerFactory.getLogger(CartProductsService.class);
    private final FavoriteProductsRepository favoriteProductsRepository;
    private final UserRepository userRepository;

    @Autowired
    public FavoriteProductsService(FavoriteProductsRepository favoriteProductsRepository, UserRepository userRepository) {
        this.favoriteProductsRepository = favoriteProductsRepository;
        this.userRepository = userRepository;
    }

    public FavoriteProducts getFavoriteProductsById(Long favoriteProductId, Principal principal) {
        User user = getUserByPrincipal(principal);
        return favoriteProductsRepository.findByIdAndUser(favoriteProductId, user)
                .orElseThrow(() -> new FavoriteProductNotFoundException("FavoriteProducts cannot be found for username: " + user.getEmail()));
    }

    public FavoriteProducts createFavoriteProducts(ProductDTO productDTO, Principal principal){
        User user = getUserByPrincipal(principal);
        FavoriteProducts favoriteProducts = new FavoriteProducts();

        favoriteProducts.setTitle(productDTO.getTitle());
        favoriteProducts.setPrice(productDTO.getPrice());
        favoriteProducts.setRating(productDTO.getRating());
        favoriteProducts.setProductURL(productDTO.getProductUrl());
        favoriteProducts.setImageURL(productDTO.getImageUrl());
        favoriteProducts.setStoreName(productDTO.getStoreName());
        favoriteProducts.setUser(user);

        LOG.info("Adding product in favorite for user: {}" , user.getUsername() );
        return favoriteProductsRepository.save(favoriteProducts);
    }

    public List<FavoriteProducts> getAllFavoriteProductsForUser(Principal principal) {
        User user = getUserByPrincipal(principal);
        return favoriteProductsRepository.findAllByUser(user);
    }

    @Transactional
    public void deleteFavoriteProducts(Long postId, Principal principal) {
        FavoriteProducts favoriteProducts = getFavoriteProductsById(postId, principal);
        favoriteProductsRepository.delete(favoriteProducts);

    }

    private User getUserByPrincipal(Principal principal) {
        String username = principal.getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found with username " + username));
    }

}
