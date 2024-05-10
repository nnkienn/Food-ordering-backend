package com.prj.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prj.api.model.Order;
import com.prj.api.model.OrderItem;

public interface OrderItemRespository extends JpaRepository<OrderItem, Long>{
	

}
