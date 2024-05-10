package com.prj.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.prj.api.dto.RestaurantDto;
import com.prj.api.model.Restaurant;
import com.prj.api.model.User;
import com.prj.api.response.CreateRestaurantRequest;
import com.prj.api.service.RestaurantService;
import com.prj.api.service.UserService;

@RestController
@RequestMapping("api/restaurants")
public class RestaurantController {
	@Autowired
	private RestaurantService restaurantSerives;

	@Autowired
	private UserService userService;
	
	@GetMapping("/search")
	public ResponseEntity<List<Restaurant>> searchRestaurant(
			@RequestHeader("Authorization") String jwt,
			@RequestParam String keyword
			) throws Exception {
		User user = userService.findUserByJwtToken(jwt);
		List<Restaurant> restaurant = restaurantSerives.searchRestaurant(keyword);
		return new ResponseEntity<>(restaurant, HttpStatus.OK);
	}
	
	@GetMapping()
	public ResponseEntity<List<Restaurant>> getAllRestaurant(
			@RequestHeader("Authorization") String jwt
			) throws Exception {
		User user = userService.findUserByJwtToken(jwt);
		List<Restaurant> restaurant = restaurantSerives.getAllRes();
		return new ResponseEntity<>(restaurant, HttpStatus.OK);
	}
	@GetMapping("/{id}")
	public ResponseEntity<Restaurant> findRestaurantById(
			@RequestHeader("Authorization") String jwt,
			@PathVariable Long id
			) throws Exception {
		User user = userService.findUserByJwtToken(jwt);
		Restaurant restaurant = restaurantSerives.findRestaurantById(id);
		return new ResponseEntity<Restaurant>(restaurant, HttpStatus.OK);
	}
	@PutMapping("/{id}/add-favorite")
	public ResponseEntity<RestaurantDto> AddToFavo(
			@RequestHeader("Authorization") String jwt,
			@PathVariable Long id
			) throws Exception {
		User user = userService.findUserByJwtToken(jwt);
		RestaurantDto restaurant = restaurantSerives.addtoFavorites(id, user);
		return new ResponseEntity<RestaurantDto>(restaurant, HttpStatus.OK);
	}
	
}
