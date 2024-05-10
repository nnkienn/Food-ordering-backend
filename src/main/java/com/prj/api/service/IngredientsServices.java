package com.prj.api.service;

import java.util.List;

import com.prj.api.model.IngedienttsItem;
import com.prj.api.model.IngredientCategory;

public interface IngredientsServices {
	public IngredientCategory createIngeredientCategory(String name, Long restaurantId) throws Exception;

	public IngredientCategory findIngredientCategoryById(Long id) throws Exception;

	public List<IngredientCategory> findIngredientCategoryByRestaurant(Long id) throws Exception;

	public IngedienttsItem createIngedienttsItem( String IngedientName, Long restaurantId, Long categoryId)
			throws Exception;

	public List<IngedienttsItem> findRestaurantIngedienttsItem(Long restaurantId);

	public IngedienttsItem updateStock(Long id) throws Exception;
}
