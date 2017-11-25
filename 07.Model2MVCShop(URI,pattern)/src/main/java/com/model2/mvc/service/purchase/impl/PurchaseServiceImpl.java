package com.model2.mvc.service.purchase.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.purchase.PurchaseDao;
import com.model2.mvc.service.purchase.PurchaseService;

@Service("purchaseServiceImpl")
public class PurchaseServiceImpl implements PurchaseService {

	@Autowired
	@Qualifier("purchaseDaoImpl")
	PurchaseDao purchaseDao;
	
	public PurchaseServiceImpl() {

	}

	@Override
	public int addPurchase(Purchase purchase) throws Exception {
		
		int addResult = purchaseDao.addPurchase(purchase);
		//System.out.println("addResult :: "+addResult);
		//System.out.println("getAmount :: "+purchase.getAmount());
		int addInventory = purchaseDao.addInventory(addResult, purchase.getAmount());
		
		return addInventory;
	}

	@Override
	public Purchase getPurchaseBytranNo(int tranNo) throws Exception {
		return purchaseDao.getPurchaseBytranNo(tranNo);
	}

	@Override
	public Purchase getPurchaseByprodNo(int prodNo) throws Exception {
		return purchaseDao.getPurchaseByprodNo(prodNo);
	}

	@Override
	public Map<String, Object> getPurchaseList(Search search, String buyerId) throws Exception {
		
		List<Purchase> list = purchaseDao.getPurchaseList(search, buyerId);
		
		int totalCount = purchaseDao.getTotalCount(search,buyerId);
		//System.out.println(search);
		//System.out.println(buyerId);
		
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("list", list);
		map.put("totalCount", totalCount);
		return map;
	}

	@Override
	public Map<String, Object> getSaleList(Search search) throws Exception {
		
		List<Purchase> list = purchaseDao.getSaleList(search);
		int totalCount = purchaseDao.getTotalCount(search);
		//System.out.println(search);
		//System.out.println(buyerId);
		
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("list", list);
		map.put("totalCount", totalCount);
		
		return map;
	}

	@Override
	public int updatePurchase(Purchase purchase) throws Exception {

		return purchaseDao.updatePurchase(purchase);
	}

	@Override
	public int updateTranCode(Purchase purchase) throws Exception {

		return purchaseDao.updateTranCode(purchase);
	}

	@Override
	public boolean checkDuplication(int prodNo) throws Exception {

		if(purchaseDao.getInventory(prodNo) >= 0) {
			return true;
		}
		
		return false;
	}

}
