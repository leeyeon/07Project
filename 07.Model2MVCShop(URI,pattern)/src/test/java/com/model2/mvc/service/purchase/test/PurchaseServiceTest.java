package com.model2.mvc.service.purchase.test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.purchase.PurchaseService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:config/context-*.xml" })
public class PurchaseServiceTest {

	@Autowired
	@Qualifier("purchaseServiceImpl")
	PurchaseService purchaseService;
	
	int prodNo = 10008;
	int tranNo = 10024;
	String buyerId = "testUserId";
	
	//@Test
	public void testAddPurchase() throws Exception {
		
		Purchase purchase = new Purchase();
		
		Product product = new Product();
		product.setProdNo(prodNo);
		purchase.setPurchaseProd(product);
		
		User user = new User();
		user.setUserId("testUserId");
		purchase.setBuyer(user);
		
		purchase.setPaymentOption("0");
		purchase.setReceiverName("테스트1");
		purchase.setReceiverPhone("010-1234-4567");
		purchase.setDivyAddr("테스트");
		purchase.setDivyRequest("테스트");
		purchase.setDivyDate("20171120");
		purchase.setAmount(1);
		
		System.out.println(":: testAddPurchase ::");
		int result = purchaseService.addPurchase(purchase);
		//==> console 확인
		System.out.println("testAddPurchase ::" + result);
		System.out.println(purchase);

	}
	
	@Test
	// tranNo 로 getPurchase...
	public void testGetPurchaseBytranNo() throws Exception {
		
		Purchase purchase = purchaseService.getPurchaseBytranNo(tranNo);
		System.out.println(purchase);
	}
	
	//@Test
	public void testGetPurchaseByprodNo() throws Exception {
		
		Purchase purchase = purchaseService.getPurchaseByprodNo(prodNo);
		System.out.println(purchase);
	}

	//@Test
	 public void testUpdatePurchase() throws Exception{
		Purchase purchase = new Purchase();
		
		Product product = new Product();
		product.setProdNo(prodNo);
		purchase.setPurchaseProd(product);
		
		User user = new User();
		user.setUserId("testUserId");
		purchase.setBuyer(user);
		
		purchase.setPaymentOption("0");
		purchase.setReceiverName("update테스트");
		purchase.setReceiverPhone("010-1234-4567");
		purchase.setDivyAddr("update테스트");
		purchase.setDivyRequest("update테스트");
		purchase.setDivyDate("20171115");
		purchase.setTranNo(tranNo);
		
		System.out.println(":: testUpdatePurchase ::");
		int result = purchaseService.updatePurchase(purchase);
		//==> console 확인
		System.out.println("testUpdatePurchase ::" + result);
		System.out.println(purchase);
		
		Purchase check = purchaseService.getPurchaseBytranNo(tranNo);
		System.out.println(check);
	 }
	 
	 //@Test
	 public void testUpdateTranCode() throws Exception{
		Purchase purchase = new Purchase();
		
		purchase.setTranCode("1");
		
		Product product = new Product();
		product.setProdNo(prodNo);
		purchase.setPurchaseProd(product);
		
		System.out.println(":: testUpdateTranCode ::");
		int result = purchaseService.updateTranCode(purchase);
		//==> console 확인
		System.out.println("testUpdateTranCode ::" + result);
		System.out.println(purchase);
		
		Purchase check = purchaseService.getPurchaseBytranNo(tranNo);
		System.out.println(check);
	 }
	 
	//@Test
	//판매배송관리...
	 public void testGetSaleList() throws Exception{
		Search search = new Search();
		search.setCurrentPage(1);
		search.setPageSize(5);
		
		Map<String, Object> map = purchaseService.getSaleList(search);
		List<Purchase> list = (List<Purchase>)map.get("list");
		int totalCount = (int)map.get("totalCount");
		
		System.out.println(":: testGetSaleList ::");
		for (Purchase purchase : list) {
			System.out.println(purchase);
		}
		System.out.println("totalCount :: " +totalCount);
	 }
	
	 //@Test
	 public void testGetPurchaseList() throws Exception{
		Search search = new Search();
		search.setCurrentPage(1);
		search.setPageSize(5);
		
		Map<String, Object> map = purchaseService.getPurchaseList(search, buyerId);
		List<Purchase> list = (List<Purchase>)map.get("list");
		int totalCount = (int)map.get("totalCount");
		
		System.out.println(":: testGetPurchaseList ::");
		for (Purchase purchase : list) {
			System.out.println(purchase);
		}
		System.out.println("totalCount :: " +totalCount);
	 }
	 
	 //@Test
	 public void testGetPurchaseListByprodName() throws Exception{
		
	 }
	 
	 //@Test
	 public void testGetPurchaseListByPrice() throws Exception{
		 
	 }
}