package com.prj.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prj.api.model.IngedienttsItem;
import com.prj.api.model.IngredientCategory;
import com.prj.api.model.Restaurant;
import com.prj.api.repository.IngredientsCategoryRespository;
import com.prj.api.repository.IngredientsItemRespository;


@Service
public class IngredientsServicesImpl implements IngredientsServices {
	@Autowired
	private IngredientsItemRespository ingredientsItemRespository;

	@Autowired
	private RestaurantService restaurantServices;

	@Autowired
	private IngredientsCategoryRespository IngredientsCategoryRespository;

	@Override
	public IngredientCategory createIngeredientCategory(String name, Long restaurantId) throws Exception {
		Restaurant restaurant = restaurantServices.findRestaurantById(restaurantId);
		IngredientCategory category = new IngredientCategory();

		category.setRestaurant(restaurant);
		category.setName(name);
		return IngredientsCategoryRespository.save(category);
	}

	@Override
	public IngredientCategory findIngredientCategoryById(Long id) throws Exception {
		// TODO Auto-generated method stub
		Optional<IngredientCategory> apt = IngredientsCategoryRespository.findById(id);
		if (apt.isEmpty()) {
			throw new Exception("Ingredient category not found");
		}
		return apt.get();
	}

	@Override
	public List<IngredientCategory> findIngredientCategoryByRestaurant(Long id) throws Exception {
		restaurantServices.findRestaurantById(id);

		// TODO Auto-generated method stub
		return IngredientsCategoryRespository.findByRestaurantId(id);
	}

	@Override
	public IngedienttsItem createIngedienttsItem(String ingredientName, Long restaurantId, Long categoryId) throws Exception {
	    Restaurant restaurant = restaurantServices.findRestaurantById(restaurantId);
	    IngredientCategory category = findIngredientCategoryById(categoryId);

	    IngedienttsItem ingredientItem = new IngedienttsItem();
	    ingredientItem.setName(ingredientName);
	    ingredientItem.setRestaurant(restaurant);
	    ingredientItem.setCategory(category);

	    IngedienttsItem ingredient = ingredientsItemRespository.save(ingredientItem);

	    category.getIngredients().add(ingredient);

	    return ingredientItem;
	}

	@Override
	public List<IngedienttsItem> findRestaurantIngedienttsItem(Long restaurantId) {
		return ingredientsItemRespository.findByRestaurantId(restaurantId);
	}

	@Override
	public IngedienttsItem updateStock(Long id) throws Exception {
		Optional<IngedienttsItem> optionalIngredientItem = ingredientsItemRespository.findById(id);
		if (optionalIngredientItem.isEmpty()) {
			throw new Exception("Ingredient not found");
		}
		IngedienttsItem ingredientsItem = optionalIngredientItem.get();
		ingredientsItem.setInStoke(!ingredientsItem.isInStoke());
		return ingredientsItemRespository.save(ingredientsItem);
	}

}
