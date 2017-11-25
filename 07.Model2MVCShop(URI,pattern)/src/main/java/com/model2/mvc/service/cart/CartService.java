package com.model2.mvc.service.cart;

import java.util.Map;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Cart;

public interface CartService {
	
	public boolean addCart(Cart cart) throws Exception;

	public Map<String, Object> getCartList(Search search, String userId) throws Exception;
	
	// 장바구니 취소!
	public boolean updateCartStatus(int cartNo) throws Exception;
}
