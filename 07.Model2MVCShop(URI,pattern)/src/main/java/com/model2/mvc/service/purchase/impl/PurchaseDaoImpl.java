package com.model2.mvc.service.purchase.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.purchase.PurchaseDao;

@Repository("purchaseDaoImpl")
public class PurchaseDaoImpl implements PurchaseDao {

	@Autowired
	@Qualifier("sqlSessionTemplate")
	private SqlSession sqlSession;
	
	public PurchaseDaoImpl() {
		// TODO Auto-generated constructor stub
	}
	
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	@Override
	public int addPurchase(Purchase purchase) throws Exception {
		sqlSession.insert("PurchaseMapper.addPurchase", purchase);
		System.out.println(purchase);
		
		return purchase.getTranNo();
	}

	@Override
	public Purchase getPurchaseBytranNo(int tranNo) throws Exception {
		return sqlSession.selectOne("PurchaseMapper.getPurchaseByTranNo", tranNo);
	}

	@Override
	public Purchase getPurchaseByprodNo(int prodNo) throws Exception {
		return sqlSession.selectOne("PurchaseMapper.getPurchaseByProdNo", prodNo);
	}

	@Override
	public List<Purchase> getPurchaseList(Search search, String buyerId) throws Exception {
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("search", search);
		map.put("buyerId", buyerId);
		map.put("listCode", "purchase");
		
		List<Purchase> list = sqlSession.selectList("PurchaseMapper.getList", map);
		
		for (Purchase purchase : list) {
			System.out.println(purchase);
			//System.out.println(purchase.getBuyer().getUserId());
			purchase.setBuyer(sqlSession.selectOne("UserMapper.getUser", buyerId));
			purchase.setPurchaseProd(sqlSession.selectOne("ProductMapper.getProduct", purchase.getPurchaseProd().getProdNo()));
		}
		
		return list;
	}

	@Override
	public List<Purchase> getSaleList(Search search) throws Exception {
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("search", search);
		map.put("listCode", "sale");
		return sqlSession.selectList("PurchaseMapper.getList", map);
	}

	@Override
	public int updatePurchase(Purchase purchase) throws Exception {
		return sqlSession.update("PurchaseMapper.updatePurchase", purchase);
	}
	
	@Override
	public int updateTranCode(Purchase purchase) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.update("PurchaseMapper.updateTranCode", purchase);
	}

	@Override
	public int updateAmount(Purchase purchase, int code) throws Exception {
		// TODO 상품 취소 구현해야됨 .. 현재 구매 뿐이라 code = 0
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("purchase", purchase);
		map.put("code", code);
		
		return sqlSession.update("PurchaseMapper.updateAmount", map);
	}

	@Override
	public int addInventory(int tranNo, int amount) throws Exception {
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("tranNo", tranNo);
		map.put("amount", amount);
		return sqlSession.insert("PurchaseMapper.addInventory", map);
	}

	@Override
	public int updateInventory(int tranNo, int amount) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getInventory(int prodNo) throws Exception {
		return sqlSession.selectOne("PurchaseMapper.checkPurchase", prodNo);
	}

	// sale
	@Override
	public int getTotalCount(Search search) throws Exception {
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("search", search);
		map.put("listCode", "sale");
		
		return sqlSession.selectOne("PurchaseMapper.getTotalCount", map);
	}
	
	// purchase
	@Override
	public int getTotalCount(Search search, String buyerId) throws Exception {
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("search", search);
		map.put("buyerId", buyerId);
		map.put("listCode", "purchase");
		//System.out.println("getTotalCount :: " +buyerId);
		return sqlSession.selectOne("PurchaseMapper.getTotalCount", map);
	}
	
}
