package com.model2.mvc.service.purchase;

import java.util.List;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Purchase;

public interface PurchaseDao {
	
	public int addPurchase(Purchase purchase) throws Exception;
	
	public Purchase getPurchaseBytranNo(int tranNo) throws Exception;
	
	public Purchase getPurchaseByprodNo(int prodNo) throws Exception;
	
	public List<Purchase> getPurchaseList(Search search, String buyerId) throws Exception;
	
	public List<Purchase> getSaleList(Search search) throws Exception;
	
	public int updatePurchase(Purchase purchase) throws Exception;
	
	public int updateTranCode(Purchase purchase) throws Exception;
	
	// code = 惑前 备概 0 / 惑前 备概 秒家 1
	public int updateAmount(Purchase purchase, int code) throws Exception;
	
	public int addInventory(int tranNo, int amount) throws Exception;
	
	public int updateInventory(int tranNo, int amount) throws Exception;
	
	public int getInventory(int prodNo) throws Exception;
	
	public int getTotalCount(Search search) throws Exception;
	
	public int getTotalCount(Search search, String buyerId) throws Exception;
	
}
