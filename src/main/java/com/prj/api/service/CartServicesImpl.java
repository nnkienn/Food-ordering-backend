package com.prj.api.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prj.api.model.Cart;
import com.prj.api.model.CartItem;
import com.prj.api.model.Food;
import com.prj.api.model.User;
import com.prj.api.repository.CartItemRepository;
import com.prj.api.repository.CartRepository;
import com.prj.api.repository.FoodRepository;
import com.prj.api.response.AddCartItemRequest;

@Service
public class CartServicesImpl implements CartServices {

	@Autowired
	private CartRepository cartRepository;

	@Autowired
	private UserService userServices;

	@Autowired
	private CartItemRepository cartItemRepository;

	@Autowired
	private FoodService foodService;

	@Override
	public CartItem addItemToCart(AddCartItemRequest req, String jwt) throws Exception {
		User user = userServices.findUserByJwtToken(jwt);
		Food food = foodService.FindFoodById(req.getFoodId());
		Cart cart = cartRepository.findByCustomerId(user.getId());

		for (CartItem cartItem : cart.getItem()) {
			if (cartItem.getFood().equals(food)) {
				int newQuantity = cartItem.getQuantity() + req.getQuantity();
				return updateCartItemQuantity(cartItem.getId(), newQuantity);
			}
		}
		CartItem newCartItem = new CartItem();
		newCartItem.setFood(food);
		newCartItem.setCart(cart);
		newCartItem.setQuantity(req.getQuantity());
		newCartItem.setIngreddients(req.getIngredients());
		newCartItem.setTotalPrice(req.getQuantity() * food.getPrice());

		CartItem saveCartItem = cartItemRepository.save(newCartItem);

		cart.getItem().add(saveCartItem);
		return saveCartItem;
	}

	@Override
	public CartItem updateCartItemQuantity(Long cartItemId, int quantity) throws Exception {
		Optional<CartItem> cartItemOptional = cartItemRepository.findById(cartItemId);
		if (cartItemOptional.isEmpty()) {
			throw new Exception("Cart item not found");
		}

		CartItem item = cartItemOptional.get();
		item.setQuantity(quantity);

		item.setTotalPrice(item.getFood().getPrice() * quantity);

		return cartItemRepository.save(item);
	}

	@Override
	public Cart removeItemFromCart(Long cartItemId, String jwt) throws Exception {
		User user = userServices.findUserByJwtToken(jwt);
		Cart cart = cartRepository.findByCustomerId(user.getId());

		Optional<CartItem> cartItemOptional = cartItemRepository.findById(cartItemId);
		if (cartItemOptional.isEmpty()) {
			throw new Exception("Cart item not found");
		}
		CartItem item = cartItemOptional.get();

		cart.getItem().remove(item);
		return cart;
	}

	@Override
	public Cart findCartByid(Long id) throws Exception {
		Optional<Cart> optionalCart = cartRepository.findById(id);
		if (optionalCart.isEmpty()) {
			throw new Exception("cart not found with id" + id);
		}
		// TODO Auto-generated method stub
		return optionalCart.get();
	}

	@Override
	public Cart findCartByUserid(Long id) throws Exception {
		Cart cart = cartRepository.findByCustomerId(id);
		cart.setTotal(caculateCartTotal(cart));
		return cart;

	}

	@Override
	public Cart clearCart(Long id) throws Exception {

		Cart cart = findCartByUserid(id);
		cart.getItem().clear();
		return cartRepository.save(cart);
	}

	@Override
	public Long caculateCartTotal(Cart cart) throws Exception {
		Long total = 0L;
		for (CartItem cartItem : cart.getItem()) {
			total += cartItem.getFood().getPrice() * cartItem.getQuantity();
		}

		// TODO Auto-generated method stub
		return total;
	}

}
