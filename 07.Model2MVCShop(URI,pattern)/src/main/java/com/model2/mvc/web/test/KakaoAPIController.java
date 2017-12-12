package com.model2.mvc.web.test;

import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.model2.mvc.common.Kakao;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.user.UserService;


@Controller
public class KakaoAPIController {

	@Autowired
	@Qualifier("userServiceImpl")
	private UserService userService;
	
	public KakaoAPIController() {
		// TODO Auto-generated constructor stub
	}
	
	@RequestMapping("/kakaoLogin")
	public String kakao(@RequestParam("code") String code,
						Model model,
						HttpSession session) throws Exception {
		
		System.out.println("code :: "+code);
		
		User userNull = new User();
		
		User user = Kakao.getAccessToken(code, userNull);
		
		// 앱 연결
		// developer 에서 login 시 signup check 풀었을 때만.
		Kakao.getAppConnection(user.getAccessToken());
		
		User result = Kakao.getUserInfo(user.getAccessToken());
		result.setAccessToken(user.getAccessToken());
		result.setRefreshToken(user.getRefreshToken());
		result.setSnsType("kakao");
		
		System.out.println(result);
		
		session.setAttribute("user", result);
		
		if(userService.getUser(result.getEmail()) == null) {
			userService.addUser(result);
		}		
        
		//return "forward:/user/addUserView.jsp";
		return "redirect:/index.jsp";
	}
	
	@RequestMapping("/kakaoLogout")
	public String kakaoLogout(HttpSession session) throws Exception {
		
		User user = (User)session.getAttribute("user");
		
		String result = Kakao.removeAppConnection(user.getAccessToken());
		
		System.out.println("kakaoLogout Result :: "+result);
		
		session.removeAttribute("user");
		
		return "redirect:/index.jsp";
	}

}
