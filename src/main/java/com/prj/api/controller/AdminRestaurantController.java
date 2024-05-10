package com.prj.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prj.api.model.Restaurant;
import com.prj.api.model.User;
import com.prj.api.response.CreateRestaurantRequest;
import com.prj.api.response.MessageResponse;
import com.prj.api.service.RestaurantService;
import com.prj.api.service.UserService;

@RestController
@RequestMapping("api/admin/restaurants")
public class AdminRestaurantController {
	@Autowired
	private RestaurantService restaurantSerives;

	@Autowired
	private UserService userService;

	@PostMapping()
	public ResponseEntity<Restaurant> createRestaurant(@RequestBody CreateRestaurantRequest req,
			@RequestHeader("Authorization") String jwt) throws Exception {
		User user = userService.findUserByJwtToken(jwt);
		Restaurant restaurant = restaurantSerives.createRestaurant(req, user);
		return new ResponseEntity<>(restaurant, HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Restaurant> updateRestaurant(@RequestBody CreateRestaurantRequest req,
			@RequestHeader("Authorization") String jwt, @PathVariable Long id) throws Exception {
		User user = userService.findUserByJwtToken(jwt);
		Restaurant restaurant = restaurantSerives.updateRestaurant(id, req);
		return new ResponseEntity<>(restaurant, HttpStatus.CREATED);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<MessageResponse> deleteRestaurant(@RequestHeader("Authorization") String jwt,
			@PathVariable Long id) throws Exception {
		User user = userService.findUserByJwtToken(jwt);
		restaurantSerives.deleteRestaurent(id);
		MessageResponse res = new MessageResponse();
		res.setMessage("restaurant delete sucess");
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@PutMapping("/{id}/status")
	public ResponseEntity<Restaurant> PutRestaurantStatus(@RequestHeader("Authorization") String jwt,
			@PathVariable Long id) throws Exception {
		User user = userService.findUserByJwtToken(jwt);
		Restaurant restaurant = restaurantSerives.updateRestaurantStatus(id);
	
		return new ResponseEntity<Restaurant>(restaurant, HttpStatus.OK);
	}
	
	

	@GetMapping("/user")
	public ResponseEntity<Restaurant> findRestaurantbyId(@RequestHeader("Authorization") String jwt) throws Exception {
		User user = userService.findUserByJwtToken(jwt);
		Restaurant restaurant = restaurantSerives.getRestaurantByUserId(user.getId());
	
		return new ResponseEntity<Restaurant>(restaurant, HttpStatus.OK);
	}


}
