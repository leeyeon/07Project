package com.model2.mvc.service.cart.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.cart.CartDao;
import com.model2.mvc.service.domain.Cart;

@Repository("cartDaoImpl")
public class CartDaoImpl implements CartDao {
	
	@Autowired
	@Qualifier("sqlSessionTemplate")
	SqlSession sqlSession;

	public CartDaoImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public int addCart(Cart cart) throws Exception {
		return sqlSession.insert("CartMapper.addCart", cart);
	}

	@Override
	public List<Cart> getCartList(Search search, String userId) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("search", search);
		map.put("userId", userId);
		
		return sqlSession.selectList("CartMapper.getCartList",map);
	}

	@Override
	public int updateCartStatus(int cartNo) throws Exception {
		return sqlSession.update("CartMapper.updateCartStatus", cartNo);
	}

	@Override
	public boolean checkCart(int prodNo) throws Exception {
		if((int)sqlSession.selectOne("CartMapper.checkCart", prodNo) == 1) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int getTotalCount(Search search, String userId) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("search", search);
		map.put("userId", userId);
		
		return sqlSession.selectOne("CartMapper.getTotalCount", map);
	}

}
