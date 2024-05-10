package com.prj.api.service;

import java.lang.StackWalker.Option;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prj.api.model.Category;
import com.prj.api.model.Restaurant;
import com.prj.api.repository.CategoryRespository;

@Service
public class CategoryServicesImpl implements CategoryServices {

	@Autowired
	private RestaurantService restaurantServices;
	
	@Autowired
	private CategoryRespository categoryRespository;
	
	
	
	@Override
	public Category createCategory(String name, Long userId)throws Exception  {
		Restaurant restaurant = restaurantServices.getRestaurantByUserId(userId);
		Category category = new Category();
		category.setName(name);
		category.setRestaurant(restaurant);
		return categoryRespository.save(category);
		
	}

	@Override
	public List<Category> findCategoryByRestaurantId(Long userId) throws Exception {
		// TODO Auto-generated method stub
		Restaurant restaurant = restaurantServices.getRestaurantByUserId(userId);
		return categoryRespository.findByRestaurantId(restaurant.getId());
	}

	@Override
	public Category findCategoryById(Long id) throws Exception {
		// TODO Auto-generated method stub
		Optional<Category> optionalCategory = categoryRespository.findById(id);
		if(optionalCategory.isEmpty()) {
			throw new Exception("category not found");
		}
		
		return optionalCategory.get();
	}

}
