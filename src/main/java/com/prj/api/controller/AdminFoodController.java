package com.prj.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prj.api.model.Food;
import com.prj.api.model.Restaurant;
import com.prj.api.model.User;
import com.prj.api.response.CreateFoodRequest;
import com.prj.api.response.MessageResponse;
import com.prj.api.service.FoodService;
import com.prj.api.service.RestaurantService;
import com.prj.api.service.UserService;

@RestController
@RequestMapping("api/admin/food")
public class AdminFoodController {
	@Autowired
	private FoodService foodServices;
	
	@Autowired
	private UserService userSerivces;
	
	@Autowired
	private RestaurantService restaurantService;
	
	@PostMapping
	public ResponseEntity<Food> createFood(@RequestBody CreateFoodRequest req, @RequestHeader("Authorization") String jwt) throws Exception{
		User user = userSerivces.findUserByJwtToken(jwt);
		Restaurant retaurant = restaurantService.findRestaurantById(req.getRestaurantId());
		Food food = foodServices.createFood(req, req.getCategory(), retaurant);
		return new ResponseEntity<>(food,HttpStatus.CREATED);
	}
	@DeleteMapping("/{id}")
	public ResponseEntity<MessageResponse> deleteFood( @PathVariable Long id,@RequestHeader("Authorization") String jwt) throws Exception{
		User user = userSerivces.findUserByJwtToken(jwt);
		
		foodServices.deleteFood(id);
		MessageResponse res = new MessageResponse();
		res.setMessage("delete sucess");
		return new ResponseEntity<MessageResponse>(res,HttpStatus.OK);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Food> updateFoodAvaibilityStatus( @PathVariable Long id,@RequestHeader("Authorization") String jwt) throws Exception{
		User user = userSerivces.findUserByJwtToken(jwt);
		
		Food food = foodServices.updateAvaiblityStatus(id);
	
		return new ResponseEntity<Food>(food,HttpStatus.OK);
	}
	
	

}
