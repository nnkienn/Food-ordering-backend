package com.prj.api.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prj.api.model.Category;
import com.prj.api.model.Food;
import com.prj.api.model.Restaurant;
import com.prj.api.repository.FoodRepository;
import com.prj.api.response.CreateFoodRequest;

@Service
public class FoodServicesImpl implements FoodService {

	@Autowired
	private FoodRepository foodRespository ;

	@Override
	public Food createFood(CreateFoodRequest req, Category category, Restaurant restaurant) {
		Food food = new Food();
		food.setFoodCategory(category);
		food.setRestaurant(restaurant);
		food.setDescription(req.getDescription());
		food.setImages(req.getImages());
		food.setName(req.getName());
		food.setIngrdients(req.getIngedients());
		food.setPrice(req.getPrice());

		Food saveFood = foodRespository.save(food);
		restaurant.getFoods().add(saveFood);
		return saveFood;
	}

	@Override
	public void deleteFood(long foodId) throws Exception {
		// TODO Auto-generated method stub
		Food food = FindFoodById(foodId);
		food.setRestaurant(null);
		foodRespository.save(food);

	}

	@Override
	public List<Food> getRestaurantFood(Long restaurantId, boolean isVegitarian, Boolean isNonVeg, boolean isSeasonal,
			String foodCategory) {
		List<Food> foods = foodRespository.findByRestaurantId(restaurantId);
		if (isVegitarian) {
			foods = filterbyVegetarian(foods, isVegitarian);
		}
		if (isNonVeg) {
			foods = filterbyisNonVeg(foods, isNonVeg);
		}
		if (isSeasonal) {
			foods = filterbySeasonal(foods, isSeasonal);
		}
		if (foodCategory != null && !foodCategory.equals("")) {

			foods = filterbycategory(foods, foodCategory);
		}

		return foods;
	}

	private List<Food> filterbycategory(List<Food> foods, String foodCategory) {
		return foods.stream().filter(food -> {
			if (food.getFoodCategory() != null) {
				return food.getFoodCategory().getName().equals(foodCategory);
			}
			return false;
		}).collect(Collectors.toList());
	}

	private List<Food> filterbySeasonal(List<Food> foods, boolean isSeasonal) {
		// TODO Auto-generated method stub
		return foods.stream().filter(food -> food.isSeasonal() == isSeasonal).collect(Collectors.toList());
	}

	private List<Food> filterbyisNonVeg(List<Food> foods, Boolean isNonVeg) {
		// TODO Auto-generated method stub
		return foods.stream().filter(food -> food.isVegetarian() == false).collect(Collectors.toList());
	}

	private List<Food> filterbyVegetarian(List<Food> foods, boolean isVegitarian) {
		// TODO Auto-generated method stub
		return foods.stream().filter(food -> food.isVegetarian() == isVegitarian).collect(Collectors.toList());
	}

	@Override
	public List<Food> searchFood(String keywords) {
		// TODO Auto-generated method stub
		return foodRespository.searchFood(keywords);
	}

	@Override
	public Food FindFoodById(Long foodid) throws Exception {
		// TODO Auto-generated method stub
		Optional<Food> optionalFood = foodRespository.findById(foodid);
		if(optionalFood.isEmpty()) {
			throw new Exception("food not exist...");
		}
		return optionalFood.get();
	}

	@Override
	public Food updateAvaiblityStatus(Long foodId) throws Exception {
		Food food = FindFoodById(foodId);
		food.setAvailable(!food.isAvailable());
		
		return foodRespository.save(food);
	}

}
