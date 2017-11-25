package com.model2.mvc.service.product.test;

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
import com.model2.mvc.service.product.ProductService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:config/context-*.xml" })
public class ProductServiceTest {

	@Autowired
	@Qualifier("productServiceImpl")
	ProductService productService;
	
	int prodNo = 10008;
	
	//@Test
	public void testAddProduct() throws Exception {
		
		Product product = new Product();
		
		product.setProdName("test");
		product.setProdDetail("test");
		product.setManuDate("20171115");
		product.setPrice(1111);
		product.setFileName("testFile");
		product.setAmount(5);
		
		System.out.println(":: testAddProduct ::");
		int result = productService.addProduct(product);
		//==> console 확인
		System.out.println("addProduct ::" + result);
		//System.out.println(product);
		
		//==> API 확인 :: 값이 같냐?
		//Assert.assertEquals("LasttestProdName", product.getProdName());
	}
	
	//@Test
	public void testGetProduct() throws Exception {
		
		System.out.println(":: testSelectOneProduct ::");
		Product product = productService.getProduct(prodNo);
		
		//System.out.println(product);
	}
	
	//@Test
	 public void testUpdateProduct() throws Exception{
		 
		Product product = new Product();
			
		product.setProdName("test1");
		product.setProdDetail("test1");
		product.setManuDate("20171115");
		product.setPrice(1111);
		product.setFileName("test1");
		product.setProdNo(prodNo);
		
		System.out.println(":: testUpdateProduct ::");
		int result = productService.updateProduct(product);
		
		//==> console 확인
		//System.out.println("updateProduct ::" + result);
		//System.out.println(product);
	 }
	
	 @Test
	 public void testGetProductList() throws Exception{
		 
		Search search = new Search();
		search.setCurrentPage(1);
		search.setPageSize(5);
		
		System.out.println(":: testUpdateProduct ::");
		Map<String, Object> map = productService.getProductList(search);
		List<Product> list = (List<Product>)map.get("list");
		int result = (int)map.get("totalCount");
		
		// 테스트!
		for(Product product: list) {
			System.out.println(product);
		} 
		
		//System.out.println("totalCount :: "+result);
		 	
	 }
	 
	 //@Test
	 public void testGetProductListByprodName() throws Exception{
		
		Search search = new Search();
		search.setCurrentPage(1);
		search.setPageSize(5);
		search.setSearchCondition("1");
		search.setSearchKeyword("보르");
		
		System.out.println(":: testUpdateProduct ::");
		Map<String, Object> map = productService.getProductList(search);
		List<Product> list = (List<Product>)map.get("list");
		int result = (int)map.get("totalCount");
		
		// 테스트!
		for(Product product: list) {
			System.out.println(product);
		} 
		
		//System.out.println("totalCount :: "+result);
	 }
	 
	 //@Test
	 public void testGetProductListByPrice() throws Exception{
		 
	 }
}