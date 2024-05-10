package com.prj.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prj.api.model.IngedienttsItem;

public interface IngredientsItemRespository extends JpaRepository<IngedienttsItem, Long> {
	List<IngedienttsItem> findByRestaurantId(Long id);
}
