package com.prj.api.service;

import java.util.List;


import com.prj.api.model.Category;

public interface CategoryServices {
	public Category createCategory(String name, Long userId) throws Exception ;
	
	public List<Category> findCategoryByRestaurantId(Long id ) throws Exception;
	
	public Category findCategoryById(Long id) throws Exception;
}
