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

public class Google {
	
	// https://www.themarketingtechnologist.co/google-oauth-2-enable-your-application-to-access-data-from-a-google-user/

	private final static String CLIENT_ID = "53828513767-euud6p6ljlmddag1odu5j48mufms1gcr.apps.googleusercontent.com";
	private final static String CLIENT_SECRET = "hytvs3HK7nWaMe0bHqlp8jct";
	private final static String SCOPE = "https://www.googleapis.com/auth/plus.login";
	private final static String REDIRECT_URI = "http://127.0.0.1:8080/googleLogin";
	
	
	private final static String GET_TOKEN_API_URL = "https://accounts.google.com/o/oauth2/token";
	private final static String PROFILE_API_URL = "https://www.googleapis.com/oauth2/v1/userinfo";
	
	/*
	 * "https://www.googleapis.com/auth/plus.login",
"https://www.googleapis.com/auth/userinfo.email",
"https://www.googleapis.com/auth/userinfo.profile",
"https://www.googleapis.com/auth/plus.me"
	 * 
	 * */

	public Google() {
		// TODO Auto-generated constructor stub
	}
	
	/* ���� ����  URL ����  */
	public String getAuthorizationUrl() {
		
		String scope = "https://www.googleapis.com/auth/plus.login "+
			"https://www.googleapis.com/auth/userinfo.email "+
			"https://www.googleapis.com/auth/userinfo.profile "+
			"https://www.googleapis.com/auth/plus.me";
		
		String url = "redirect:https://accounts.google.com/o/oauth2/auth?client_id="+CLIENT_ID
				+"&response_type=code&scope="+scope
				+"&redirect_uri="+REDIRECT_URI;
		
		return url;
	}
	
	/* ����� ��ū ��� */
	public User getAccessToken(HttpSession session, String code) throws Exception {
		
		System.out.println("getAccessToken(String code, User user)..............");
		
		String param = "grant_type=authorization_code&client_id="+CLIENT_ID
						+"&redirect_uri="+REDIRECT_URI+"&code="+code+"&client_secret="+CLIENT_SECRET;
		
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
        } else {  // ���� �߻�
            br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
        }
        
        //JSON Data �б�
        String jsonData = "";
        StringBuffer response = new StringBuffer();
        
        while ((jsonData = br.readLine()) != null) {
            response.append(jsonData);
        }
        
        br.close();
        
        // Console Ȯ��
        System.out.println(response.toString());
        JSONParser parser = new JSONParser();
        JSONObject obj = (JSONObject)parser.parse(response.toString());
        
        System.out.println("access_token :: "+ obj.get("access_token"));

        User user = new User();
        user.setAccessToken(obj.get("access_token").toString());
        //user.setRefreshToken(obj.get("refresh_token").toString());
        
		return user;
		
	}
	
	// ����� ���� �޾ƿ���
	public User getUserProfile(User user) throws Exception {
		
		System.out.println("getUserProfile(User user)...........");
		
		String param = "?alt=json&access_token="+user.getAccessToken();
        
        URL url = new URL(PROFILE_API_URL+param);
        HttpURLConnection con = (HttpURLConnection)url.openConnection();
        
        con.setRequestMethod("GET");
        
        // Response Code
        int responseCode = con.getResponseCode();
        
        BufferedReader br = null;
        
        if(responseCode==200) { 
        	// UTF-8 �� �־��ָ�,,,, �ѱ��ھ� �޾ƿ� �� ���ڵ� ���� �ذ��.
            br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
        } else {  // ���� �߻�
            br = new BufferedReader(new InputStreamReader(con.getErrorStream(), "UTF-8"));
        }
        
        //JSON Data �б�
        String jsonData = "";
        StringBuffer response = new StringBuffer();
        
        while ((jsonData = br.readLine()) != null) {
            response.append(jsonData);
        }
        
        br.close();
        
        // Console Ȯ��
        System.out.println("response :: "+ response.toString());
        JSONParser parser = new JSONParser();
        JSONObject obj = (JSONObject)parser.parse(response.toString());
        
        user.setUserId(obj.get("email").toString());
        user.setEmail(obj.get("email").toString());       
        user.setUserName(obj.get("name").toString());
        user.setProfileImage(obj.get("picture").toString());
        user.setSnsType("google");
		
		return user;
	}

}
