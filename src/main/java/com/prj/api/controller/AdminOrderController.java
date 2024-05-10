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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.prj.api.model.Order;
import com.prj.api.model.User;
import com.prj.api.response.OrderRequest;
import com.prj.api.service.OrderServices;
import com.prj.api.service.UserService;

@RestController
@RequestMapping("api/admin")
public class AdminOrderController {
	@Autowired
	private OrderServices orderService;

	@Autowired
	private UserService userServices;

	@PostMapping("/order")


	@GetMapping("/order/restaurant/{id}")
	public ResponseEntity<List<Order>> getOrderHistory(@PathVariable Long id,@RequestBody OrderRequest req, @RequestParam(required = false) String orderStatus,
			@RequestHeader("Authorization") String jwt) throws Exception {

		User user = userServices.findUserByJwtToken(jwt);
		List<Order> order = orderService.getResttaurantOrder(id, orderStatus);
		return new ResponseEntity<List<Order>>(order, HttpStatus.OK);
	}

	@PutMapping("/order/{orderid}/{orderStatus}")
	public ResponseEntity<Order> updateorderStatus(@PathVariable Long orderid,@PathVariable String orderStatus,
			@RequestHeader("Authorization") String jwt) throws Exception {

		User user = userServices.findUserByJwtToken(jwt);
		Order order = orderService.updatetOrder(orderid, orderStatus);
		return new ResponseEntity<Order>(order, HttpStatus.OK);
	}
}
