package com.model2.mvc.common;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.model2.mvc.service.domain.User;
import com.sun.xml.internal.fastinfoset.Encoder;

public class Kakao {

	public static String REST_KET = "90d9379d1246c1e7e36d34027d2e497d";
	public static String REDIRECT_URI = "http://127.0.0.1:8080/kakaoLogin";
	
	public Kakao() {
		// TODO Auto-generated constructor stub
	}
	
	// login access_token 얻기
	public static User getAccessToken(String code, User user) throws Exception {
		
		System.out.println("getAccessToken(String code)..............");
		
		String kakaoAPIURL = "https://kauth.kakao.com/oauth/token";
		String param = "grant_type=authorization_code&client_id="+REST_KET
						+"&redirect_uri="+REDIRECT_URI+"&code="+code;
		
		URL url = new URL(kakaoAPIURL);
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

        user.setAccessToken(obj.get("access_token").toString());
        user.setRefreshToken(obj.get("refresh_token").toString());
        
		return user;
		
	}
	
	// 회원가입 (앱 연결)
	public static String getAppConnection(String accessToken) throws Exception {
		
		System.out.println("getAppConnection(String accessToken)............");
		
		String kakaoAPIURL = "https://kapi.kakao.com/v1/user/signup";
        
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
        
        if(obj.get("access_token") != null) {
        	return obj.get("access_token").toString();
        } else {
        	return "error";
        }		
	}
	
	// 앱 탈퇴
	public static String removeAppConnection(String accessToken) throws Exception {
		
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
	
	// 사용자 정보 받아오기
	public static User getUserInfo(String accessToken) throws Exception {
		
		System.out.println("getUserInfo(String accessToken)...........");
		
		String kakaoAPIURL = "https://kapi.kakao.com/v1/user/me";
        
        URL url = new URL(kakaoAPIURL);
        HttpURLConnection con = (HttpURLConnection)url.openConnection();
        
        con.setRequestMethod("POST");
        con.setRequestProperty("Authorization", "Bearer "+accessToken);
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
        
        User user = new User();
        user.setEmail(obj.get("kaccount_email").toString());
        user.setUserId(obj.get("kaccount_email").toString());
        
        JSONObject properties = (JSONObject)parser.parse(obj.get("properties").toString());
        user.setProfileImage(properties.get("profile_image").toString());
        user.setUserName(properties.get("nickname").toString());
		
		return user;
	}
}
