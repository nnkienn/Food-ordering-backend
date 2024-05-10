package com.prj.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prj.api.model.Category;

public interface CategoryRespository extends JpaRepository<Category, Long> {
	public List<Category> findByRestaurantId(Long id);

}
