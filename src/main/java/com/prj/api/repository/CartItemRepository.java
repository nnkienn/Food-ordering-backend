package com.prj.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prj.api.model.Cart;
import com.prj.api.model.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}
