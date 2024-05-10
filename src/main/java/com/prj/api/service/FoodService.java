package com.prj.api.service;

import java.util.List;

import com.prj.api.model.Category;
import com.prj.api.model.Food;
import com.prj.api.model.Restaurant;
import com.prj.api.response.CreateFoodRequest;

public interface FoodService {
	public Food createFood(CreateFoodRequest req, Category category,Restaurant restaurant);
	void deleteFood(long foodId) throws Exception;
	
	public List<Food> getRestaurantFood(Long restaurantId,boolean isVegitarian ,Boolean isNonVeg ,boolean isSeasonal,String foodCategory);
	public List<Food> searchFood(String keywords);
	public Food FindFoodById(Long foodid) throws Exception;

	public Food updateAvaiblityStatus(Long foodId) throws Exception;
}
