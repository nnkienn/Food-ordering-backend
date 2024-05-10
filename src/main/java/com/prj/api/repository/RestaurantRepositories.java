package com.prj.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.prj.api.model.Restaurant;

public interface RestaurantRepositories extends JpaRepository<Restaurant, Long> {

	@Query("SELECT r FROM Restaurant r WHERE lower(r.name) LIKE lower(concat('%', :query, '%')) OR lower(r.cuisineType) LIKE lower(concat('%', :query, '%'))")

	List<Restaurant> findBySearchQuery(String query);
	
	
	Restaurant findByOwnerId(Long UserId);
}
