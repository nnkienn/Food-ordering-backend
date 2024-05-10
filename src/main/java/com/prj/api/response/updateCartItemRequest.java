package com.prj.api.response;

import lombok.Data;

@Data
public class updateCartItemRequest {
	private Long cartItemId;
	
	private int quantity;
}
