package com.prj.api.response;

import java.util.List;

import com.prj.api.model.Address;
import com.prj.api.model.ContacttInformatin;

import lombok.Data;

@Data
public class CreateRestaurantRequest {
	private Long id;
	private String name;
	private String description;
	private String cuisineType;
	private Address address;
	private ContacttInformatin contactInformation;
	private String openingHours;
	private List<String> images;
	
	
}
