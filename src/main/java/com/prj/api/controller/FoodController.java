package com.prj.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.prj.api.model.Food;
import com.prj.api.model.Restaurant;
import com.prj.api.model.User;
import com.prj.api.response.CreateFoodRequest;
import com.prj.api.service.FoodService;
import com.prj.api.service.RestaurantService;
import com.prj.api.service.UserService;

@RestController
@RequestMapping("api/food")
public class FoodController {
	@Autowired
	private FoodService foodServices;

	@Autowired
	private UserService userSerivces;

	@Autowired
	private RestaurantService restaurantService;

	@GetMapping("/search")
	public ResponseEntity<List<Food>> searchFood(@RequestParam String name, @RequestHeader("Authorization") String jwt)
			throws Exception {
		User user = userSerivces.findUserByJwtToken(jwt);

		List<Food> foods = foodServices.searchFood(name);

		return new ResponseEntity<>(foods, HttpStatus.CREATED);
	}

	@GetMapping("/restaurant/{restaurantId}")
	public ResponseEntity<List<Food>> getRestaurantFood(@PathVariable Long restaurantId,
			@RequestParam boolean vegatarian, @RequestParam boolean seasonal, @RequestParam boolean nonveg,
			@RequestParam(required = false) String food_category, @RequestHeader("Authorization") String jwt)
			throws Exception {
		User user = userSerivces.findUserByJwtToken(jwt);

		List<Food> foods = foodServices.getRestaurantFood(restaurantId, vegatarian, nonveg, seasonal, food_category);

		return new ResponseEntity<>(foods, HttpStatus.OK);
	}
}
