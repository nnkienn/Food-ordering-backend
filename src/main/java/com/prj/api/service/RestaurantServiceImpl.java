package com.prj.api.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prj.api.dto.RestaurantDto;
import com.prj.api.model.Address;
import com.prj.api.model.Restaurant;
import com.prj.api.model.User;
import com.prj.api.repository.AddressRespository;
import com.prj.api.repository.RestaurantRepositories;
import com.prj.api.repository.UserRespository;
import com.prj.api.response.CreateRestaurantRequest;

@Service
public class RestaurantServiceImpl implements RestaurantService {

	@Autowired
	private RestaurantRepositories restaurantRepo;

	@Autowired
	private AddressRespository addressRepo;

	@Autowired
	private UserRespository userRepo;

	@Override
	public Restaurant createRestaurant(CreateRestaurantRequest req, User user) {
		Address address = addressRepo.save(req.getAddress());

		Restaurant restaurant = new Restaurant();

		restaurant.setAddress(address);
		restaurant.setContactInformation(req.getContactInformation());
		restaurant.setCuisineType(req.getCuisineType());
		restaurant.setDescription(req.getDescription());
		restaurant.setImages(req.getImages());
		restaurant.setName(req.getName());
		restaurant.setOpeningHours(req.getOpeningHours());
		restaurant.setRegisttationDate(LocalDateTime.now());
		restaurant.setOwner(user);
		// TODO Auto-generated method stub
		return restaurantRepo.save(restaurant);
	}

	@Override
	public Restaurant updateRestaurant(Long restaurantId, CreateRestaurantRequest updateRestaurant) throws Exception {
		Restaurant restaurant = findRestaurantById(restaurantId);
		if (restaurant.getCuisineType() != null) {
			restaurant.setCuisineType(updateRestaurant.getCuisineType());

		}
		if (restaurant.getDescription() != null) {
			restaurant.setDescription(updateRestaurant.getDescription());

		}
		if (restaurant.getName() != null) {
			restaurant.setName(updateRestaurant.getName());

		}
		return restaurantRepo.save(restaurant);
	}

	@Override
	public void deleteRestaurent(Long restaurantId) throws Exception {
		Restaurant restaurant = findRestaurantById(restaurantId);
		restaurantRepo.delete(restaurant);

	}

	@Override
	public List<Restaurant> getAllRes() {
		// TODO Auto-generated method stub
		return restaurantRepo.findAll();
	}

	@Override
	public List<Restaurant> searchRestaurant(String keywords) {

		return restaurantRepo.findBySearchQuery(keywords);
	}

	@Override
	public Restaurant findRestaurantById(Long restaurantId) throws Exception {
		Optional<Restaurant> opt = restaurantRepo.findById(restaurantId);
		if (opt.isEmpty()) {
			throw new Exception("restaurant not found with id" + restaurantId);
		}
		return opt.get();
	}

	@Override
	public Restaurant getRestaurantByUserId(Long userId) throws Exception {
		Restaurant restaurant = restaurantRepo.findByOwnerId(userId);
		if (restaurant == null) {
			throw new Exception("restaurant not found with id" + userId);
		}
		return restaurant;
	}

	@Override
	public RestaurantDto addtoFavorites(Long restaurantId, User user) throws Exception {
		Restaurant restaurant = findRestaurantById(restaurantId);
		RestaurantDto dto = new RestaurantDto();
		dto.setDescription(restaurant.getDescription());
		dto.setImages(restaurant.getImages());
		dto.setTitle(restaurant.getName());
		dto.setId(restaurant.getId());
		if (user.getFavorites().contains(dto)) {
			user.getFavorites().remove(dto);
		} else
			user.getFavorites().add(dto);

		userRepo.save(user);
		return dto;
	}

	@Override
	public Restaurant updateRestaurantStatus(Long restaurantId) throws Exception {
		Restaurant restaurant = findRestaurantById(restaurantId);
		restaurant.setOpen(!restaurant.isOpen());
		return restaurantRepo.save(restaurant);
	}

}
