package com.prj.api.response;

import lombok.Data;

@Data
public class IngredientCategoryRequest {
	private String name;
	private Long restaurantId;
}
