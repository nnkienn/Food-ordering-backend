package com.prj.api.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.prj.api.dto.RestaurantDto;
import com.prj.api.model.Restaurant;
import com.prj.api.model.User;
import com.prj.api.response.CreateRestaurantRequest;


public interface RestaurantService {
	public Restaurant createRestaurant(CreateRestaurantRequest req, User user);

	public Restaurant updateRestaurant(Long restaurantId, CreateRestaurantRequest updateRestaurant) throws Exception;

	public void deleteRestaurent(Long restaurantId) throws Exception;

	public List<Restaurant> getAllRes();

	public List<Restaurant> searchRestaurant(String keywords);
	public Restaurant findRestaurantById(Long restaurantId) throws Exception;

	public Restaurant getRestaurantByUserId(Long userId) throws Exception;

	public RestaurantDto addtoFavorites(Long restaurantId, User user) throws Exception;

	public Restaurant updateRestaurantStatus(Long restaurantId) throws Exception;

}
