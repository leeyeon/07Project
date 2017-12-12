package com.model2.mvc.rest.purchase;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.user.UserService;

@RestController
@RequestMapping("/purchase/*")
public class PurchaseRestController {

	@Autowired
	@Qualifier("purchaseServiceImpl")
	private PurchaseService purchaseService;
	
	@Autowired
	@Qualifier("userServiceImpl")
	private UserService userService;
	
	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;
	
	public PurchaseRestController() {
		System.out.println(this.getClass());
	}
	
	@Value("#{commonProperties['pageUnit']}")
	// @Value("#{commonProperties['pageUnit'] ?: 3}")
	int pageUnit;

	@Value("#{commonProperties['pageSize']}")
	// @Value("#{commonProperties['pageSize'] ?: 2}")
	int pageSize;

	@RequestMapping(value="/json/addPurchase/{prodNo}/{buyerId}", method=RequestMethod.POST)
	public boolean addPurchase(@PathVariable Integer prodNo,
							@PathVariable String buyerId,
							@RequestBody Purchase purchase) throws Exception {
		
		System.out.println("/purchase/json/addPurchase : POST");
		
		User buyer = userService.getUser(buyerId);
		Product product = productService.getProduct(prodNo.intValue());
		
		purchase.setBuyer(buyer);
		purchase.setPurchaseProd(product);
		
		int result = purchaseService.addPurchase(purchase);
		
		if(result==1) {
			return true;
		} else {
			return false;
		}
	}
	
	@RequestMapping(value="json/getPurchase/{isNo}/{no}", method=RequestMethod.GET)
	public Purchase getPurchase(@PathVariable String isNo,
								@PathVariable Integer no) throws Exception {
		
		System.out.println("/purchase/json/getPurchase : GET");
		
		Purchase purchase = purchaseService.getPurchase(no.intValue(), isNo);
		
		return purchase;
	}
	
	@RequestMapping(value="json/updatePurchase", method=RequestMethod.POST)
	public boolean updatePurchase(@RequestBody Purchase purchase) throws Exception {
		
		System.out.println("/purchase/json/updatePurchase : POST");
		
		int result = purchaseService.updatePurchase(purchase);
		
		if(result == 1) {
			return true;
		} else {
			return false;
		}
	}
	
	@RequestMapping(value="json/listPurchase/{buyerId}", method=RequestMethod.POST)
	public Map listPurchase(@PathVariable String buyerId,
							@RequestBody Search search) throws Exception {
		
		System.out.println("/purchase/json/listPurchase : POST");
		
		if(search.getCurrentPage() == 0 ){
			search.setCurrentPage(1);
		}
		search.setPageSize(pageSize);
		
		Map<String, Object> map = purchaseService.getPurchaseList(search, buyerId);
		
		Page resultPage	= 
				new Page( search.getCurrentPage(), ((Integer)map.get("totalCount")).intValue(), 
				pageUnit, pageSize);
		
		Map<String , Object> result = new HashMap<String, Object>();
		
		result.put("list", map.get("list"));
		result.put("resultPage", resultPage);
		result.put("search", search);
		
		return result;
	}
	
	@RequestMapping(value="json/listSale", method=RequestMethod.POST)
	public Map listSale(@RequestBody Search search) throws Exception {
		
		System.out.println("/purchase/json/listPurchase : POST");
		
		if(search.getCurrentPage() == 0 ){
			search.setCurrentPage(1);
		}
		search.setPageSize(pageSize);
		
		Map<String, Object> map = purchaseService.getSaleList(search);
		Page resultPage	= 
				new Page( search.getCurrentPage(), ((Integer)map.get("totalCount")).intValue(), 
				pageUnit, pageSize);
		
		Map<String , Object> result = new HashMap<String, Object>();
		
		result.put("list", map.get("list"));
		result.put("resultPage", resultPage);
		result.put("search", search);
		
		return result;
	}
	
	@RequestMapping(value="json/updateTranCode")
	public boolean updateTranCode(@RequestBody Purchase purchase) throws Exception {
		
		int result = purchaseService.updateTranCode(purchase);
		
		//purchase.setTranNo(tranNo);
		//purchase.setTranCode(tranCode);
		
		if(result == 1) {
			return true;
		} else {
			return false;
		}
	}
	
	//@RequestMapping(value="json/updateTranCodeByProd/{prodNo}")
	public boolean updateTranCodeByProd(@PathVariable Integer prodNo,
									@RequestBody Purchase purchase) throws Exception {
		
		Product product = new Product();
		product.setProdNo(prodNo.intValue());
		
		purchase.setPurchaseProd(product);
		// purchase.setTranCode(tranCode);
		
		int result = purchaseService.updateTranCode(purchase);
		if(result == 1) {
			return true;
		} else {
			return false;
		}
	}
}
