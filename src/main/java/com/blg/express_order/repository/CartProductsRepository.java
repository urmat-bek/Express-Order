package com.blg.express_order.repository;

import com.blg.express_order.entity.CartProducts;
import com.blg.express_order.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartProductsRepository extends JpaRepository<CartProducts, Long> {
    @Override
    Optional<CartProducts> findById(Long aLong);

    @Override
    List<CartProducts> findAll();

    List<CartProducts> findAllByUser(User user);
    Optional<CartProducts> findCartProductsByIdAndUser(Long id, User user);


}
