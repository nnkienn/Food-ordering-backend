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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prj.api.model.IngedienttsItem;
import com.prj.api.model.IngredientCategory;
import com.prj.api.response.IngredientCategoryRequest;
import com.prj.api.response.IngredientRequest;
import com.prj.api.service.IngredientsServices;

@RestController
@RequestMapping("/api/admin/ingredients")
public class IngredientController {
	@Autowired
	private IngredientsServices ingredientsServices;

	@PostMapping("/category")
	public ResponseEntity<IngredientCategory> crreateIngredientCategory(@RequestBody IngredientCategoryRequest req) throws Exception {
		IngredientCategory items= ingredientsServices.createIngeredientCategory(req.getName(), req.getRestaurantId());
		return new ResponseEntity<>(items,HttpStatus.CREATED);

	}
	@PostMapping
	public ResponseEntity<IngedienttsItem> crreateIngredientItem(@RequestBody IngredientRequest req) throws Exception {
		IngedienttsItem items= ingredientsServices.createIngedienttsItem(req.getName(), req.getRestaurantId(),req.getCategoryId());
		return new ResponseEntity<>(items,HttpStatus.CREATED);
	}
	
	@PutMapping("/{id}/stoke")
	public ResponseEntity<IngedienttsItem> updateIngredientStock(@PathVariable Long id) throws Exception {
		IngedienttsItem items= ingredientsServices.updateStock(id);
		return new ResponseEntity<IngedienttsItem>(items,HttpStatus.OK);
	}
	@GetMapping("/restaurant/{id}")
	public ResponseEntity<List<IngedienttsItem>> getRestaurantIngredient(@PathVariable Long id) throws Exception {
		List<IngedienttsItem> items= ingredientsServices.findRestaurantIngedienttsItem(id);
		return new ResponseEntity<List<IngedienttsItem>>(items,HttpStatus.OK);
	}
	@GetMapping("/restaurant/{id}/category")
	public ResponseEntity<List<IngredientCategory>> getRestaurantIngredientCategory(@PathVariable Long id) throws Exception {
		List<IngredientCategory> items= ingredientsServices.findIngredientCategoryByRestaurant(id);
		return new ResponseEntity<List<IngredientCategory>>(items,HttpStatus.OK);
	}
}
