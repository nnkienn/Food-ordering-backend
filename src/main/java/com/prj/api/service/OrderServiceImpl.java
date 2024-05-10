package com.prj.api.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prj.api.model.Address;
import com.prj.api.model.Cart;
import com.prj.api.model.CartItem;
import com.prj.api.model.Order;
import com.prj.api.model.OrderItem;
import com.prj.api.model.Restaurant;
import com.prj.api.model.User;
import com.prj.api.repository.AddressRespository;
import com.prj.api.repository.OrderItemRespository;
import com.prj.api.repository.OrderRespository;
import com.prj.api.repository.RestaurantRepositories;
import com.prj.api.repository.UserRespository;
import com.prj.api.response.OrderRequest;

@Service
public class OrderServiceImpl implements OrderServices {

	@Autowired
	private OrderRespository orderRespository;

	@Autowired
	private OrderItemRespository orderItemRepository;

	@Autowired
	private AddressRespository addressRepository;

	@Autowired
	private UserRespository userRepository;

	@Autowired
	private RestaurantService restaurantService;

	@Autowired
	private CartServices cartService;

	@Override
	public Order createtOrder(OrderRequest order, User user) throws Exception {
		Address shipAddress = order.getDeliveryAddress();

		Address saveAddress = addressRepository.save(shipAddress);

		if (!user.getAddresses().contains(saveAddress)) {
			user.getAddresses().add(saveAddress);
		}

		Restaurant resttaurant = restaurantService.findRestaurantById(order.getRestaurantId());

		Order createOrder = new Order();

		createOrder.setCustomer(user);
		createOrder.setCreatedAt(new Date());
		createOrder.setOrderStatus("PENDING");
		createOrder.setDeliveryAddress(saveAddress);
		createOrder.setRestaurant(resttaurant);

		Cart cart = cartService.findCartByUserid(user.getId());

		List<OrderItem> orderItems = new ArrayList<>();

		for (CartItem cartItem : cart.getItem()) {
			OrderItem orderItem = new OrderItem();
			orderItem.setFood(cartItem.getFood());
			orderItem.setIngredient(cartItem.getIngreddients());
			orderItem.setQuantity(cartItem.getQuantity());
			orderItem.setTotalPrice(cartItem.getTotalPrice());
			OrderItem saveOrderItem = orderItemRepository.save(orderItem);
			orderItems.add(saveOrderItem);
		}
		Long totalPrice = cartService.caculateCartTotal(cart);

		createOrder.setItems(orderItems);
		createOrder.setTotalPrice(totalPrice);

		Order saveOrder = orderRespository.save(createOrder);
		resttaurant.getOrders().add(saveOrder);
		// TODO Auto-generated method stub
		return createOrder;
	}

	@Override
	public Order updatetOrder(Long OrderId, String orderStatus) throws Exception {
		Order order = findOrderById(OrderId);
		if (orderStatus.equals("OUT_FOR_DELIVERY") || orderStatus.equals("DELIVERED") || orderStatus.equals("COMPLETED")
				|| orderStatus.equals("PENDING")) {
			order.setOrderStatus(orderStatus);
			return orderRespository.save(order);
		}

		// TODO Auto-generated method stub
		throw new Exception("Please select a valid order Status");
	}

	@Override
	public void cancalOrder(Long orderId) throws Exception {
		Order order = findOrderById(orderId);
		orderRespository.deleteById(orderId);
		// TODO Auto-generated method stub

	}

	@Override
	public List<Order> getUserOrder(Long userId) throws Exception {
		// TODO Auto-generated method stub
		return orderRespository.findByCustomerId(userId);
	}

	@Override
	public List<Order> getResttaurantOrder(Long restaurantId, String OrderStatus) throws Exception {
		List<Order> orders = orderRespository.findByRestaurantId(restaurantId);
		if(OrderStatus!=null) {
			orders =orders.stream().filter(order->order.getOrderStatus().equals(OrderStatus)).collect(Collectors.toList());
		}
		// TODO Auto-generated method stub
		return orders;
	}

	@Override
	public Order findOrderById(Long id) throws Exception {
		Optional<Order> optionalOrder = orderRespository.findById(id);
		if(optionalOrder.isEmpty()) {
			throw new Exception("order nott found");
		}
		return optionalOrder.get();
	}

}
