package com.blg.express_order.repository;

import com.blg.express_order.entity.FavoriteProducts;
import com.blg.express_order.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FavoriteProductsRepository extends JpaRepository<FavoriteProducts, Long> {
    @Override
    Optional<FavoriteProducts> findById(Long aLong);

    @Override
    List<FavoriteProducts> findAll();

    List<FavoriteProducts> findAllByUser(User user);
    Optional<FavoriteProducts> findByIdAndUser(Long id, User user);

}
