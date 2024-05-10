package com.prj.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prj.api.model.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {

	Cart findByCustomerId(Long id);

}
