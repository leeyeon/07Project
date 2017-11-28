package com.model2.mvc.web.cart;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.cart.CartService;
import com.model2.mvc.service.domain.Cart;

@Controller
@RequestMapping("/cart/*")
public class CartController {

	@Autowired
	@Qualifier("cartServiceImpl")
	CartService cartService;
	
	public CartController() {
		System.out.println(this.getClass());
	}
	
	public String getCart() {
		
		return null;
	}
	
	@RequestMapping(value="listCart", method=RequestMethod.GET)
	public String listCart(@RequestParam("userId") String userId,
							Model model) throws Exception {
		
		Search search = new Search();
	 	search.setCurrentPage(1);
	 	search.setPageSize(3);
	 	
	 	Map<String,Object> map = cartService.getCartList(search, userId);
	 	List<Cart> list =  (List<Cart>)map.get("list");
	 	int totalCount = (int)map.get("totalCount");
	 	
	 	for (Cart cart : list) {
			System.out.println(cart);
		}
	 	
	 	System.out.println("totalCount :: " +totalCount);
	 	
	 	model.addAttribute("list", list);
	 	model.addAttribute("totalCount", totalCount);
		
		return "forward:/cart/listCart.jsp";
	}

}
