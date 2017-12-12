package com.model2.mvc.web.test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/test/*")
public class TestController {

	public TestController() {
		
	}
	
	@RequestMapping(value="multiFileUpload", method= RequestMethod.GET)
	public String MultiFileUpload() {
		
		System.out.println("/test/MultiFileUpload :: GET");
		
		return "forward:/test/multiFileUpload.jsp";
	}
	
	@RequestMapping(value="multiFileUpload", method= RequestMethod.POST)
	public String MultiFileUpload(@RequestParam("uploadFile") List<MultipartFile> uploadFiles,
										Model model) {
		
		System.out.println("/test/MultiFileUpload :: POST");
		
		List<String> result = new ArrayList<String>();
		
		if(uploadFiles != null && uploadFiles.size() > 0) {
			for (MultipartFile file : uploadFiles) {
				String fileName = file.getOriginalFilename();
				System.out.println("fileName :: "+fileName);
				
				File loc = new File(
						"C:\\Users\\301-6\\git\\07Project\\07.Model2MVCShop(URI,pattern)\\WebContent\\images\\testUploadFiles\\"+fileName);
				
				try {
					// 해당 주소로 파일 옮기기 & 파일이 이미 있으면 override됨.
					file.transferTo(loc);
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				result.add(fileName);
			}
		}
		
		model.addAttribute("list", result);
		
		return "forward:/test/multiFileUploadResult.jsp";		
	}

}
