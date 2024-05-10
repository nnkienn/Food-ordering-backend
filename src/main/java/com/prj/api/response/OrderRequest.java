package com.prj.api.response;

import com.prj.api.model.Address;

import lombok.Data;

@Data
public class OrderRequest {
	private Long restaurantId;
	private Address deliveryAddress;
}
