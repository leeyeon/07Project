package com.model2.mvc.rest.product;

import java.io.File;
import java.util.Map;

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
	
	@RequestMapping(value="json/addProduct")
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
	
	@RequestMapping(value="json/getProduct/{prodNo}", method= {RequestMethod.GET})
	public Product getProduct(@PathVariable int prodNo) throws Exception {
		
		System.out.println("product/json/getProduct :: GET / POST");
		
		return productService.getProduct(prodNo);
	}

}
