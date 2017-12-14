package com.model2.mvc.web.test;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.model2.mvc.common.Google;
import com.model2.mvc.common.Kakao;
import com.model2.mvc.common.Naver;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.user.UserService;


@Controller
@RequestMapping("/*")
public class APIController {

	@Autowired
	@Qualifier("userServiceImpl")
	private UserService userService;
	
	@Autowired
	private Kakao kakao;
	
	@Autowired
	private Naver naver;
	
	@Autowired
	private Google google;

	public APIController() {
		
	}
	
	
	public void setKakao(Kakao kakao) {
		this.kakao = kakao;
	}
	
	
	public void setNaver(Naver naver){
		this.naver = naver;
	}
	
	public void setGoogle(Google google) {
		this.google = google;
	}

	/* Kakao 로그인 */
	@RequestMapping(value= "kakaoLoginRequest", method=RequestMethod.GET)
	public String kakaoLogin() {
		
		System.out.println("[ kakao Login Request ]");
		
		return kakao.getAuthorizationUrl();
	}
	
	@RequestMapping("kakaoLogin")
	public String kakao(@RequestParam("code") String code,
						HttpSession session) throws Exception {
		
		System.out.println("code :: "+code);
		
		User user = kakao.getAccessToken(code);
		
		
		kakao.getAppConnection(user.getAccessToken());
		
		user = kakao.getUserProfile(user);

		session.setAttribute("user", user);
		
		// 앱 연결
		// developer 에서 login 시 signup check 풀었을 때만.
		if(userService.getUser(user.getEmail()) == null) {
			userService.addUser(user);
		}
		
		return "redirect:/index.jsp";
	}
	
	/* Kakao 탈퇴 */
	@RequestMapping("kakaoLogout")
	public String kakaoLogout(HttpSession session) throws Exception {
		
		User user = (User)session.getAttribute("user");
		String result = kakao.removeAppConnection(user.getAccessToken());
		
		session.removeAttribute("user");
		
		return "redirect:/index.jsp";
	}
	
	/* Naver 로그인 */
	@RequestMapping(value= "naverLoginRequest", method=RequestMethod.GET)
	public String naverLogin(HttpSession session) {
		
		System.out.println(" [ Naver Login Request ]");
		
		String url = naver.getAuthorizationUrl(session);
		
		return url;
	}
	
	@RequestMapping("naverLogin")
	public String naverLogin(@RequestParam("code") String code,
							@RequestParam("state") String state,
							HttpSession session) throws Exception {
		
		System.out.println(" [ Naver Login ] getToken && getUserInfo");
		
		User user = naver.getAccessToken(session, code, state);
		user = naver.getUserProfile(user);
		
		if(userService.getUser(user.getEmail()) == null) {
			userService.addUser(user);
		}
		
		return "redirect:/index.jsp";
	}

	/* Google 로그인 */
	@RequestMapping(value = "/googleLoginRequest", method = { RequestMethod.GET, RequestMethod.POST })
	public String login(HttpSession session) throws Exception {
		
		System.out.println("[ google Login Request ]");
		
		return google.getAuthorizationUrl();
	}

	@RequestMapping(value = "/googleLogin", method = { RequestMethod.GET, RequestMethod.POST })
	public String googleCallback(@RequestParam String code,
								HttpSession session) throws Exception {
		
		System.out.println(" [ Google Login ] getToken && getUserInfo");
		
		//System.out.println("code :: "+code);
		
		User user = google.getAccessToken(session, code);
		user = google.getUserProfile(user);
		
		session.setAttribute("user", user);
		
		if(userService.getUser(user.getEmail()) == null) {
			userService.addUser(user);
		}

		return "redirect:/index.jsp";
	}
}
