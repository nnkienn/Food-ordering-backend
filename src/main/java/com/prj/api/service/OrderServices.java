package com.prj.api.service;

import java.util.List;

import com.prj.api.model.Order;
import com.prj.api.model.User;
import com.prj.api.response.OrderRequest;

public interface OrderServices {
	public Order createtOrder(OrderRequest order,User user) throws Exception;
	
	public Order updatetOrder(Long OrderId,String OrderStatus) throws Exception;
	
	public void cancalOrder(Long orderId) throws Exception;
	
	public List<Order> getUserOrder(Long userId)  throws Exception;
	
	public List<Order> getResttaurantOrder(Long restaurantId,String OrderStatus)  throws Exception;
	
	
	public Order findOrderById(Long id) throws Exception;
	
}
