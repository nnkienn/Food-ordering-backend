package com.prj.api.response;

import java.util.List;

import com.prj.api.model.Category;
import com.prj.api.model.IngedienttsItem;

import lombok.Data;

@Data
public class CreateFoodRequest {
	private String name;
	private String description;
	private Long price;
	private Category category;
	
	private List<String> images;
	private Long restaurantId;
	private boolean vegetarin;
	
	private boolean seasional;
	
	private List<IngedienttsItem> Ingedients;
}
