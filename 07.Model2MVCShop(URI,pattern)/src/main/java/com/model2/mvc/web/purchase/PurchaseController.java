package com.model2.mvc.web.purchase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.user.UserService;
import com.sun.istack.internal.Nullable;

@Controller
@RequestMapping("/purchase/*")
public class PurchaseController {

	@Autowired
	@Qualifier("purchaseServiceImpl")
	private PurchaseService purchaseService;
	
	@Autowired
	@Qualifier("userServiceImpl")
	private UserService userService;
	
	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;
	
	public PurchaseController() {
		System.out.println(this.getClass());
	}
	
	@Value("#{commonProperties['pageUnit']}")
	// @Value("#{commonProperties['pageUnit'] ?: 3}")
	int pageUnit;

	@Value("#{commonProperties['pageSize']}")
	// @Value("#{commonProperties['pageSize'] ?: 2}")
	int pageSize;
	
	@RequestMapping(value="addPurchase", method=RequestMethod.GET)
	public String addPurchase(@RequestParam("prodNo") int prodNo,
								Model model) throws Exception {
		
		Product product = productService.getProduct(prodNo);
		model.addAttribute("product", product);
		
		return "forward:/purchase/addPurchaseView.jsp";
	}
	
	@RequestMapping(value="addPurchase", method=RequestMethod.POST)
	public String addPurchase(@RequestParam("prodNo") int prodNo,
							@RequestParam("buyerId") String buyerId,
							@ModelAttribute("purchase") Purchase purchase,
							Model model) throws Exception {
		
		User buyer = userService.getUser(buyerId);
		Product product = productService.getProduct(prodNo);
		
		purchase.setBuyer(buyer);
		purchase.setPurchaseProd(product);
		
		int result = purchaseService.addPurchase(purchase);
		model.addAttribute("purchase", purchase);
		
		if(result == 1) {
			return "forward:/purchase/addPurchase.jsp";
		} else {
			return null;
		}
	}
	
	@RequestMapping(value="getPurchase")
	public String getPurchase(@RequestParam(value="tranNo", required=false) Integer tranNo,
							 @RequestParam(value="prodNo", required=false) Integer prodNo,
							Model model) throws Exception {
		
		Purchase purchase = null;
		//System.out.println("tranNo :: "+tranNo);
		if(tranNo != null) {
			purchase = purchaseService.getPurchase(tranNo.intValue(), "tranNo");
		} else {
			//System.out.println("prodNo 으로 갔습니당");
			purchase = purchaseService.getPurchase(prodNo.intValue(), "prodNo");
		}
		
		model.addAttribute("purchase", purchase);
		
		return "forward:/purchase/getPurchase.jsp";
	}
	
	@RequestMapping(value="updatePurchase", method=RequestMethod.GET)
	public String updatePurchaseView(@RequestParam("tranNo") int tranNo,
									Model model) throws Exception {
		
		Purchase purchase = purchaseService.getPurchase(tranNo, "tranNo");
		//System.out.println(purchase.getDivyDate());
		
		// yyyy-mm-dd hh:mm:ss -> YYYYMMDD
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
		Date date = dateFormat.parse(purchase.getDivyDate());
		
		System.out.println(date+" :: "+(new SimpleDateFormat("YYYYMMDD")).format(date));
		
		purchase.setDivyDate((new SimpleDateFormat("YYYYMMDD")).format(date));
		model.addAttribute("purchase", purchase);
		
		return "forward:/purchase/updatePurchase.jsp";
	}
	
	@RequestMapping(value="updatePurchase", method=RequestMethod.POST)
	public String updatePurchase(@RequestParam("tranNo") int tranNo,
								@ModelAttribute("purchase") Purchase purchase) throws Exception {
		
		int result = purchaseService.updatePurchase(purchase);
		
		if(result == 1) {
			return "redirect:/purchase/getPurchase?tranNo="+tranNo;
		} else {
			return null;
		}
	}
	
	@RequestMapping(value="listPurchase")
	public String listPurchase(HttpSession session,
							@RequestParam(value="menu", required=false) String menu,
							@ModelAttribute("search") @Nullable Search search,
							Model model)throws Exception {
		
		String buyerId = ((User)session.getAttribute("user")).getUserId();
		System.out.println(buyerId);
		
		if(search.getCurrentPage() ==0 ){
			search.setCurrentPage(1);
		}
		search.setPageSize(pageSize);
		
		Map<String, Object> map = purchaseService.getPurchaseList(search, buyerId);
		
		Page resultPage	= 
				new Page( search.getCurrentPage(), ((Integer)map.get("totalCount")).intValue(), 
				pageUnit, pageSize);
		
		model.addAttribute("list", map.get("list"));
		model.addAttribute("resultPage", resultPage);
		model.addAttribute("search", search);
		model.addAttribute("menu", menu);
		
		return "forward:/purchase/listPurchase.jsp";
	}
	
	@RequestMapping(value="listSale")
	public String listSale(@RequestParam(value="menu", required=false) String menu,
						@ModelAttribute("search") @Nullable Search search,
						Model model) throws Exception {
		
		if(search.getCurrentPage() ==0 ){
			search.setCurrentPage(1);
		}
		search.setPageSize(pageSize);
		
		Map<String, Object> map = purchaseService.getSaleList(search);
		
		Page resultPage	= 
				new Page( search.getCurrentPage(), ((Integer)map.get("totalCount")).intValue(), 
				pageUnit, pageSize);
		
		model.addAttribute("list", map.get("list"));
		model.addAttribute("resultPage", resultPage);
		model.addAttribute("search", search);
		model.addAttribute("menu", menu);
		
		return "forward:/purchase/listSale.jsp";
	}
	
	@RequestMapping(value="updateTranCode")
	public String updateTranCode(@RequestParam("tranNo") int tranNo,
								@RequestParam("tranCode") String tranCode) throws Exception {
		
		Purchase purchase = new Purchase();
		purchase.setTranNo(tranNo);
		purchase.setTranCode(tranCode);
		
		int result = purchaseService.updateTranCode(purchase);
		if(result == 1) {
			return "forward:/purchase/listSale?menu=manage";
		} else {
			return null;
		}
	}
	
	@RequestMapping(value="updateTranCodeByProd")
	public String updateTranCodeByProd(@RequestParam("prodNo") int prodNo,
									@RequestParam("tranCode") String tranCode) throws Exception {
		
		Purchase purchase = new Purchase();
		Product product = new Product();
		product.setProdNo(prodNo);
		
		purchase.setPurchaseProd(product);
		purchase.setTranCode(tranCode);
		
		int result = purchaseService.updateTranCode(purchase);
		if(result == 1) {
			return "forward:/purchase/listSale?menu=manage";
		} else {
			return null;
		}
	}

}
