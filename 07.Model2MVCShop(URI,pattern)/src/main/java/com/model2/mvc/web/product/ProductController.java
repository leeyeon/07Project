package com.model2.mvc.web.product;

import java.io.File;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;
import com.sun.istack.internal.Nullable;

@Controller
@RequestMapping("/product/*")
public class ProductController {

	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;

	public ProductController() {
		System.out.println(this.getClass());
	}

	@Value("#{commonProperties['pageUnit']}")
	// @Value("#{commonProperties['pageUnit'] ?: 3}")
	int pageUnit;

	@Value("#{commonProperties['pageSize']}")
	// @Value("#{commonProperties['pageSize'] ?: 2}")
	int pageSize;
	
	@RequestMapping(value="addProduct", method=RequestMethod.GET)
	public String addProductView() {
		
		System.out.println("/product/addProduct : GET");
		
		return "forward:/product/addProduct.jsp";
	}
	
	
	@RequestMapping(value="addProduct", method=RequestMethod.POST)
	public String addProduct(@ModelAttribute("product") Product product,
							@RequestParam("uploadFile") MultipartFile uploadFile) throws Exception {
		
		System.out.println("/product/addProduct : POST");
		
		product.setManuDate(product.getManuDate().replaceAll("-", "").trim());
		
		//MultipartFile uploadFile = product.getUploadFile();
		System.out.println("uploadFile :: "+product.getUploadFile());
		if(uploadFile != null) {
			String fileName = uploadFile.getOriginalFilename();
			System.out.println("fileName :: " +fileName);
			product.setFileName(fileName);
			
			File file = new File(
					"C:\\Users\\301-6\\git\\07Project\\07.Model2MVCShop(URI,pattern)\\WebContent\\images\\uploadFiles\\"+fileName);
			uploadFile.transferTo(file);
		}
		
		int result = productService.addProduct(product);
		
		if(result == 1) {
			return "forward:/product/addProduct.jsp";
		} else {
			return null;
		}
	}
	
	@RequestMapping(value="getProduct", method= {RequestMethod.GET, RequestMethod.POST})
	public String getProduct(@RequestParam("prodNo") int prodNo,
							@RequestParam(value="menu", required=false) String menu,
							@CookieValue(value="history", required=false) String history,
							HttpServletResponse response,
							Model model) throws Exception  {
		
		System.out.println("getProduct Start");
		
		Product product = productService.getProduct(prodNo);
		model.addAttribute("product", product);
		
		if("manage".equals(menu)) {
			return "redirect:/product/updateProduct?prodNo="+prodNo+"&menu="+menu;
		} else if("search".equals(menu)) {
			
			Cookie cookie = null;
			
			System.out.println("history before cookie :: " +history);
			
			if(history != null) {
				cookie = new Cookie("history", history+","+prodNo);
				System.out.println("history after cookie (not null) :: " + history+","+prodNo);
			} else {
				cookie = new Cookie("history", prodNo+"");
				System.out.println("history after cookie (null) :: " +prodNo);
			}
			
			response.addCookie(cookie);
			
			
			
			return "forward:/product/getProduct.jsp?menu="+menu;
		}
		
		return "forward:/product/getProduct.jsp?menu="+menu;
	}
	
	@RequestMapping(value="updateProduct", method=RequestMethod.GET)
	public String updateProductView(@RequestParam("prodNo") int prodNo,
									@RequestParam("menu") String menu,
									Model model) throws Exception  {
		Product product = productService.getProduct(prodNo);
		
		model.addAttribute("product", product);
		model.addAttribute("menu", menu);
		
		System.out.println("updateProductView menu:: "+menu);
		
		return "forward:/product/updateProduct.jsp";
	}
	
	@RequestMapping(value="updateProduct", method=RequestMethod.POST)
	public String updateProduct(@ModelAttribute("product") Product product) throws Exception  {
		
		String menu = "ok";
		
		//System.out.println(prodNo);
		//product.setProdNo(prodNo);
		product.setManuDate(product.getManuDate().replaceAll("-", "").trim());
		
		// file upload
		MultipartFile uploadFile = product.getUploadFile();
		if(uploadFile != null) {
			String fileName = uploadFile.getOriginalFilename();
			System.out.println("fileName :: " +fileName);
			product.setFileName(fileName);
			
			File file = new File(
					"C:\\Users\\301-6\\git\\07Project\\07.Model2MVCShop(URI,pattern)\\WebContent\\images\\uploadFiles\\"+fileName);
			uploadFile.transferTo(file);
		}
		
		int result = productService.updateProduct(product);
		
		System.out.println("updateProduct.do prodNo:: "+product.getProdNo());
		System.out.println("updateProduct.do menu:: "+menu);
		
		// prodNo 이 자동으로 넘어가는 이유?
		
		if(result == 1) {
			//System.out.println("요기11111");
			return "forward:/product/getProduct?prodNo="+product.getProdNo()+"&menu="+menu;
		} else {
			//System.out.println("요기22222");
			return null;
		}
	}
	
	@RequestMapping(value="listProduct")
	public String listProduct(@RequestParam("menu") String menu,
							@ModelAttribute("search") @Nullable Search search,
							Model model) throws Exception  {
		
		System.out.println("/product/listProduct");
		
		if(search.getCurrentPage() ==0 ){
			search.setCurrentPage(1);
		}
		search.setPageSize(pageSize);
		
		Map<String, Object> map = productService.getProductList(search);
		
		Page resultPage	= 
				new Page( search.getCurrentPage(), ((Integer)map.get("totalCount")).intValue(), 
				pageUnit, pageSize);
		
		model.addAttribute("list", map.get("list"))
			 .addAttribute("resultPage", resultPage)
			 .addAttribute("search", search)
			 .addAttribute("menu", menu);
		
		return "forward:/product/listProduct.jsp";
	}
	
	@RequestMapping(value="history")
	public String history(@CookieValue(value="history", defaultValue="0") String history,
								Model model) {
		
		String[] result = null;
		
		if(!history.isEmpty()) {
			result = history.split(",");
		}
		
		model.addAttribute("history", result);
		System.out.println(result.length);
		
		//System.out.println("history.isEmpty() ? :: "+history.isEmpty());
			
		return "forward:/history.jsp";
	}

}
