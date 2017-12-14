package com.model2.mvc.common;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.model2.mvc.service.domain.User;

public class Naver {
	
	private final static String CLIENT_ID = "ynLHu5Y1qRtLT74HiGOR";
	private final static String CLIENT_SECRET = "pY0Eno9_9Q";
	private final static String REDIRECT_URI = "http://127.0.0.1:8080/naverLogin";
	private final static String SESSION_STATE = "oauth_state";
	private final static String PROFILE_API_URL = "https://openapi.naver.com/v1/nid/me";
	private final static String GET_TOKEN_API_URL = "https://nid.naver.com/oauth2.0/token";	

	public Naver() {
		// TODO Auto-generated constructor stub
	}
	
	/* 네아로 인증  URL 생성  Method */
	public String getAuthorizationUrl(HttpSession session) {
		
		String state = generateRandomString();
		
		this.setSession(session, state);
		
		String url = "redirect:https://nid.naver.com/oauth2.0/authorize?response_type=code"
				+"&client_id="+Naver.CLIENT_ID+"&state="+state
				+"&redirect_uri="+Naver.REDIRECT_URI;
		
		System.out.println("getAuthorizationUrl :: "+url);
		
		return url;
	}
	
	/* 네아로 Callback 처리 및  AccessToken 획득 Method */
	public User getAccessToken(HttpSession session, String code, String state) throws Exception{
		
		/* Callback으로 전달받은 세선검증용 난수값과 세션에 저장되어있는 값이 일치하는지 확인 */
		String sessionState = getSession(session);
		
		if(sessionState.equals(state)){
			
			String naverURL = GET_TOKEN_API_URL+"?grant_type=authorization_code&client_id="+CLIENT_ID
					+"&client_secret="+CLIENT_SECRET+"&code="+code;
			
			URL url = new URL(naverURL);
	        HttpURLConnection con = (HttpURLConnection)url.openConnection();
	        con.setRequestMethod("GET");
	        
	        // Response Code GET
	        BufferedReader br = null;
	        
	        if(con.getResponseCode() == 200) {
	            br = new BufferedReader(new InputStreamReader(con.getInputStream()));
	        } else {  // 에러 발생
	            br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
	        }
        	
	        //JSON Data 읽기
            String jsonData = "";
            StringBuffer response = new StringBuffer();
            
            while ((jsonData = br.readLine()) != null) {
                response.append(jsonData);
            }
            
            br.close();
            
            System.out.println("naver return json :: "+ response.toString());
            JSONParser parser = new JSONParser();
            JSONObject obj = (JSONObject)parser.parse(response.toString());
            
            User user = new User();
            user.setRefreshToken(obj.get("refresh_token").toString());
            user.setAccessToken(obj.get("access_token").toString());
            
            session.setAttribute("user", user);
			
            return user;
		}
		return null;
	}
	
	/* 네이버 사용자 프로필 API를 호출 */
	public User getUserProfile(User user) throws Exception {

		URL url = new URL(PROFILE_API_URL);
        HttpURLConnection con = (HttpURLConnection)url.openConnection();
        
        con.setRequestMethod("GET");
        con.setRequestProperty("Authorization", "Bearer "+user.getAccessToken());
        
        // Response Code GET
        BufferedReader br = null;
        
        if(con.getResponseCode() == 200) {
            br = new BufferedReader(new InputStreamReader(con.getInputStream()));
        } else {  // 에러 발생
            br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
        }
    	
        //JSON Data 읽기
        String jsonData = "";
        StringBuffer response = new StringBuffer();
        
        while ((jsonData = br.readLine()) != null) {
            response.append(jsonData);
        }
        
        br.close();
        
        System.out.println("naver response :: "+ response.toString());
        JSONParser parser = new JSONParser();
        JSONObject obj = (JSONObject)parser.parse(response.toString());
        
        JSONObject userInfo = (JSONObject)parser.parse(obj.get("response").toString());
        
        user.setProfileImage(userInfo.get("profile_image").toString());
        user.setEmail(userInfo.get("email").toString());
        user.setUserName(userInfo.get("name").toString());
        user.setUserId(userInfo.get("email").toString());
        user.setSnsType("naver");
        //age, gender, birthday ...
        
		return user;
	}
	
	/* 세션 유효성 검증을 위한 난수 생성기 */
	private String generateRandomString() {
		return UUID.randomUUID().toString();
	}
	
	/* http session에 데이터 저장 */
	private void setSession(HttpSession session,String state){
		session.setAttribute(SESSION_STATE, state);	
	}

	/* http session에서 데이터 가져오기 */	
	private String getSession(HttpSession session){
		return (String) session.getAttribute(SESSION_STATE);
	}

}
