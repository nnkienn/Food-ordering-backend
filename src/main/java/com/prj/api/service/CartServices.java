package com.prj.api.service;

import com.prj.api.model.Cart;
import com.prj.api.model.CartItem;
import com.prj.api.model.User;
import com.prj.api.response.AddCartItemRequest;

public interface CartServices {
	public CartItem addItemToCart(AddCartItemRequest req ,String jwt) throws Exception;
	public CartItem updateCartItemQuantity(Long cartItemId,int quantity) throws Exception;
	public Cart removeItemFromCart(Long cartItemId,String jwt) throws Exception;
	public Long caculateCartTotal(Cart cart ) throws Exception;
	public Cart findCartByid(Long id) throws Exception;
	public Cart findCartByUserid(Long id) throws Exception;
	public Cart clearCart(Long id)throws Exception;

}
