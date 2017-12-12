package com.model2.mvc.rest.product;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;

@RestController
@RequestMapping("/product/*")
public class ProductRestController {

	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;

	public ProductRestController() {
		System.out.println(this.getClass());
	}

	@Value("#{commonProperties['pageUnit']}")
	// @Value("#{commonProperties['pageUnit'] ?: 3}")
	int pageUnit;

	@Value("#{commonProperties['pageSize']}")
	// @Value("#{commonProperties['pageSize'] ?: 2}")
	int pageSize;
	
	@RequestMapping(value="json/addProduct", method=RequestMethod.POST)
	public boolean addProduct(@RequestBody Product product) throws Exception {
		
		System.out.println("/product/addProduct : POST");
		
		product.setManuDate(product.getManuDate().replaceAll("-", "").trim());
		
		MultipartFile uploadFile = product.getUploadFile();
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
			return true;
		} else {
			return false;
		}
	}
	
	@RequestMapping(value="json/getProduct/{prodNo}", method= RequestMethod.GET)
	public Product getProduct(@PathVariable int prodNo) throws Exception {
		
		System.out.println("product/json/getProduct :: GET / POST");
		
		return productService.getProduct(prodNo);
	}
	
	@RequestMapping(value="json/updateProduct", method= RequestMethod.POST)
	public Product updateProduct(@RequestBody Product product) throws Exception {
		
		//System.out.println(product);
		
		product.setManuDate(product.getManuDate().replaceAll("-", "").trim());
		
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
		
		if(result == 1) {
			return product;
		} else {
			return null;
		}
	}

	@RequestMapping( value="json/listProduct", method=RequestMethod.POST )
	public Map listProduct( @RequestBody Search search ) throws Exception {
		
		System.out.println("/product/json/listProduct : POST");
		
		if(search.getCurrentPage() ==0 ){
			search.setCurrentPage(1);
		}
		search.setPageSize(pageSize);
		
		System.out.println(search);
		
		// Business logic ผ๖วเ
		Map<String , Object> map = productService.getProductList(search);
		
		Page resultPage = new Page( search.getCurrentPage(), ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		System.out.println(resultPage);
		
		Map<String , Object> result = new HashMap<String, Object>();
		result.put("list", map.get("list"));
		result.put("resultPage", resultPage);
		result.put("search", search);
		
		return result;
	}
	
	@RequestMapping( value="json/listProductName", method=RequestMethod.POST )
	public List<String> lisProductName ( @RequestParam("prodName") String prodName) throws Exception {
		
		System.out.println("listProductName :: "+prodName);
		
		JSONArray array = new JSONArray();
		JSONObject obj = null;
		
		List<String> list = productService.getProductNameList(prodName);
		
		return list;
	}
}
