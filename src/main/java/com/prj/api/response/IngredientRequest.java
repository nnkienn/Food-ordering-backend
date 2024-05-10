package com.prj.api.response;

import lombok.Data;

@Data
public class IngredientRequest {
	private String name;
	private Long restaurantId;
	private Long categoryId;
}
