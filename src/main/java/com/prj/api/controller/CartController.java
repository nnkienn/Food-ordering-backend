package com.prj.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prj.api.model.Cart;
import com.prj.api.model.CartItem;
import com.prj.api.model.User;
import com.prj.api.response.AddCartItemRequest;
import com.prj.api.response.updateCartItemRequest;
import com.prj.api.service.CartServices;
import com.prj.api.service.UserService;

@RestController
@RequestMapping("/api")
public class CartController {
	@Autowired
	private CartServices cartService;
	
	@Autowired
	private UserService userService;
	
	@PutMapping("/cart/add/")
	public ResponseEntity<CartItem> addItemToCart(@RequestBody AddCartItemRequest req,@RequestHeader("Authorization") String jwt) throws Exception{
		CartItem cartItem = cartService.addItemToCart(req, jwt);
		return new ResponseEntity<CartItem>(cartItem,HttpStatus.OK);
	}
	@PutMapping("/cart-item/update")
	public ResponseEntity<CartItem> updateItemQuantity(@RequestBody updateCartItemRequest req,@RequestHeader("Authorization") String jwt) throws Exception{
		CartItem cartItem = cartService.updateCartItemQuantity(req.getCartItemId(), req.getQuantity());
		return new ResponseEntity<CartItem>(cartItem,HttpStatus.OK);
	}

	@DeleteMapping("/cart-item/{id}/remove")
	public ResponseEntity<Cart> removeItem(@PathVariable Long id,@RequestHeader("Authorization") String jwt) throws Exception{
		Cart cartItem = cartService.removeItemFromCart(id, jwt);
		return new ResponseEntity<Cart>(cartItem,HttpStatus.OK);
	}
	@PutMapping("/cart/clear")
	public ResponseEntity<Cart> findUserCart(@RequestHeader("Authorization") String jwt) throws Exception{
		User user = userService.findUserByJwtToken(jwt);
		Cart cartItem = cartService.findCartByUserid(user.getId());
		return new ResponseEntity<Cart>(cartItem,HttpStatus.OK);
	}
	@DeleteMapping("/cart")
	public ResponseEntity<Cart> clearCart(@RequestHeader("Authorization") String jwt) throws Exception{
		User user = userService.findUserByJwtToken(jwt);
		Cart cartItem = cartService.clearCart(user.getId());
		return new ResponseEntity<Cart>(cartItem,HttpStatus.OK);
	}
}
