package com.model2.mvc.service.cart.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.cart.CartDao;
import com.model2.mvc.service.cart.CartService;
import com.model2.mvc.service.domain.Cart;

@Repository("cartServiceImpl")
public class CartServiceImpl implements CartService {

	@Autowired
	@Qualifier("cartDaoImpl")
	CartDao cartDao;
	
	public CartServiceImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean addCart(Cart cart) throws Exception {
		
		if(!cartDao.checkCart(cart.getProdNo())) {
			cartDao.addCart(cart);
			return true;
		}

		return false;
	}

	@Override
	public Map<String, Object> getCartList(Search search, String userId) throws Exception {
		
		List<Cart> list = cartDao.getCartList(search, userId);
		
		int totalCount = cartDao.getTotalCount(search, userId);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", list);
		map.put("totalCount", totalCount);
		
		return map;
	}

	@Override
	public boolean updateCartStatus(int cartNo) throws Exception {
		
		int result = cartDao.updateCartStatus(cartNo);
		
		if(result == 1) {
			return true; 
		}
		
		return false;
	}

}
