package com.prj.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prj.api.model.IngredientCategory;

public interface IngredientsCategoryRespository extends JpaRepository<IngredientCategory, Long> {
List<IngredientCategory> findByRestaurantId(Long id);
}

