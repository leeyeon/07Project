package com.model2.mvc.common;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.model2.mvc.service.domain.User;
import com.sun.xml.internal.fastinfoset.Encoder;

public class Kakao {

	private final static String CLIENT_ID = "90d9379d1246c1e7e36d34027d2e497d";
	private final static String REDIRECT_URI = "http://127.0.0.1:8080/kakaoLogin";
	private final static String GET_TOKEN_API_URL = "https://kauth.kakao.com/oauth/token";
	private final static String PROFILE_API_URL = "https://kapi.kakao.com/v1/user/me";

	public Kakao() {
		// TODO Auto-generated constructor stub
	}
	
	/* 카카오 인증  URL 생성  */
	public String getAuthorizationUrl() {
		
		String url = "redirect:https://kauth.kakao.com/oauth/authorize?client_id="+Kakao.CLIENT_ID
				+"&redirect_uri="+Kakao.REDIRECT_URI+"&response_type=code";
		
		return url;
	}
	
	/* 사용자 토큰 얻기 */
	public User getAccessToken(String code) throws Exception {
		
		System.out.println("getAccessToken(String code, User user)..............");
		
		String param = "grant_type=authorization_code&client_id="+CLIENT_ID
						+"&redirect_uri="+REDIRECT_URI+"&code="+code;
		
		URL url = new URL(GET_TOKEN_API_URL);
        HttpURLConnection con = (HttpURLConnection)url.openConnection();
        
        con.setRequestMethod("POST");
        con.setDoOutput(true);
        con.setDoInput(true);
        con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        
        OutputStream os = con.getOutputStream();
        
        os.write(param.getBytes());
        os.flush();
        os.close();
        
        // Response Code
        int responseCode = con.getResponseCode();
        
        BufferedReader br = null;
        
        if(responseCode==200) { 
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
        
        // Console 확인
        System.out.println(response.toString());
        JSONParser parser = new JSONParser();
        JSONObject obj = (JSONObject)parser.parse(response.toString());
        
        System.out.println("access_token :: "+ obj.get("access_token"));

        User user = new User();
        user.setAccessToken(obj.get("access_token").toString());
        user.setRefreshToken(obj.get("refresh_token").toString());
        
		return user;
		
	}
	
	// 사용자 정보 받아오기
	public User getUserProfile(User user) throws Exception {
		
		System.out.println("getUserInfo(String accessToken)...........");
        
        URL url = new URL(PROFILE_API_URL);
        HttpURLConnection con = (HttpURLConnection)url.openConnection();
        
        con.setRequestMethod("POST");
        con.setRequestProperty("Authorization", "Bearer "+user.getAccessToken());
        con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
        
        // Response Code
        int responseCode = con.getResponseCode();
        
        BufferedReader br = null;
        
        if(responseCode==200) { 
        	// UTF-8 로 넣어주면,,,, 한글자씩 받아올 때 인코딩 문제 해결됨.
            br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
        } else {  // 에러 발생
            br = new BufferedReader(new InputStreamReader(con.getErrorStream(), "UTF-8"));
        }
        
        //JSON Data 읽기
        String jsonData = "";
        StringBuffer response = new StringBuffer();
        
        while ((jsonData = br.readLine()) != null) {
            response.append(jsonData);
        }
        
        br.close();
        
        // Console 확인
        System.out.println("response :: "+ response.toString());
        JSONParser parser = new JSONParser();
        JSONObject obj = (JSONObject)parser.parse(response.toString());
        JSONObject properties = (JSONObject)parser.parse(obj.get("properties").toString());
        
        user.setEmail(obj.get("kaccount_email").toString());
        user.setUserId(obj.get("kaccount_email").toString());
        user.setProfileImage(properties.get("profile_image").toString());
        user.setUserName(properties.get("nickname").toString());
        user.setSnsType("kakao");
		
		return user;
	}
	
	// 회원가입 (앱 연결)
	public String getAppConnection(String token) throws Exception {
		
		System.out.println("getAppConnection(String accessToken)............");
		
		String kakaoAPIURL = "https://kapi.kakao.com/v1/user/signup";
        
        URL url = new URL(kakaoAPIURL);
        HttpURLConnection con = (HttpURLConnection)url.openConnection();
        
        con.setRequestMethod("POST");
        con.setRequestProperty("Authorization", "Bearer "+token);
        con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
        
        // Response Code
        int responseCode = con.getResponseCode();
        
        BufferedReader br = null;
        
        if(responseCode==200) { 
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
        
        // Console 확인
        System.out.println("response :: "+ response.toString());
        JSONParser parser = new JSONParser();
        JSONObject obj = (JSONObject)parser.parse(response.toString());
        
        System.out.println("access_token :: "+ obj.get("access_token"));
        
        if(obj.get("access_token") != null) {
        	return obj.get("access_token").toString();
        } else {
        	return "error";
        }		
	}
	
	// 앱 탈퇴
	public String removeAppConnection(String accessToken) throws Exception {
		
		System.out.println("getAppConnection(String accessToken)............");
		
		String kakaoAPIURL = "https://kapi.kakao.com/v1/user/unlink";
        
        URL url = new URL(kakaoAPIURL);
        HttpURLConnection con = (HttpURLConnection)url.openConnection();
        
        con.setRequestMethod("POST");
        con.setRequestProperty("Authorization", "Bearer "+accessToken);
        con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
        
        // Response Code
        int responseCode = con.getResponseCode();
        
        BufferedReader br = null;
        
        if(responseCode==200) { 
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
        
        // Console 확인
        System.out.println("response :: "+ response.toString());
        JSONParser parser = new JSONParser();
        JSONObject obj = (JSONObject)parser.parse(response.toString());
        
        System.out.println("access_token :: "+ obj.get("access_token"));
        
        if(obj.get("id") != null) {
        	return obj.get("id").toString();
        } else {
        	return "error";
        }		
	}
}
