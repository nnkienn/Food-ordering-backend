package com.prj.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prj.api.model.Category;
import com.prj.api.model.User;
import com.prj.api.service.CategoryServices;
import com.prj.api.service.UserService;

@RestController
@RequestMapping("api")
public class CategoryController {

	@Autowired
	private CategoryServices categoryServices;

	@Autowired
	private UserService userServices;

	@PostMapping("/admin/category")
	public ResponseEntity<Category> createCategory(@RequestBody Category category,
			@RequestHeader("Authorization") String jwt) throws Exception {
		User user = userServices.findUserByJwtToken(jwt);
		Category createCategory = categoryServices.createCategory(category.getName(), user.getId());
		return new ResponseEntity<Category>(createCategory, HttpStatus.CREATED);

	}

	@GetMapping("/category/restaurant")
	public ResponseEntity<List<Category>> getRestaurantCategory(@RequestHeader("Authorization") String jwt)
			throws Exception {
		User user = userServices.findUserByJwtToken(jwt);
		List<Category> categories = categoryServices.findCategoryByRestaurantId(user.getId());
		return new ResponseEntity<List<Category>>(categories, HttpStatus.CREATED);

	}
}
