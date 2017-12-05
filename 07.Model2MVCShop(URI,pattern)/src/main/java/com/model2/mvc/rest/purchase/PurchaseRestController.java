package com.model2.mvc.rest.purchase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
	
	@RequestMapping(value="json/getPurchase/{isNo}/{no}")
	public Purchase getPurchase(@PathVariable String isNo,
								@PathVariable Integer no) throws Exception {
		
		Purchase purchase = purchaseService.getPurchase(no.intValue(), isNo);
		
		return purchase;
	}
}
