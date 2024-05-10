package com.prj.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prj.api.model.CartItem;
import com.prj.api.model.Order;
import com.prj.api.model.User;
import com.prj.api.response.AddCartItemRequest;
import com.prj.api.response.OrderRequest;
import com.prj.api.service.OrderServices;
import com.prj.api.service.UserService;

@RestController
@RequestMapping("/api")
public class OrderController {

	@Autowired
	private OrderServices orderService;

	@Autowired
	private UserService userServices;

	@PostMapping("/order")
	public ResponseEntity<Order> createOrder(@RequestBody OrderRequest req, @RequestHeader("Authorization") String jwt)
			throws Exception {

		User user = userServices.findUserByJwtToken(jwt);
		Order order = orderService.createtOrder(req, user);

		return new ResponseEntity<Order>(order, HttpStatus.OK);
	}

	@GetMapping("/order/user")
	public ResponseEntity<List<Order>> getOrderHistory(@RequestBody OrderRequest req,
			@RequestHeader("Authorization") String jwt) throws Exception {

		User user = userServices.findUserByJwtToken(jwt);
		List<Order> order = orderService.getUserOrder(user.getId());
		return new ResponseEntity<List<Order>>(order, HttpStatus.OK);
	}
}
