package com.globelabs.api;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class Auth extends GlobeApi {
	public static final String LOGIN_URL = "http://%s/dialog/oauth";
	public static final String AUTH_URL = "http://%s/oauth/access_token";
	
	protected String key;
	protected String secret;
	
	public Auth(String key, String secret) {
		this.key = key;
		this.secret = secret;
	}
	
	public String getLoginUrl() {
		return String.format(Auth.LOGIN_URL, GlobeApi.AUTH_POINT)+"?app_id="+this.key;
	}
	
	public void getAccessToken(String code, PostRequestHandler handler) {
		try {
			String key = URLEncoder.encode(this.key, "utf-8");
			String secret = URLEncoder.encode(this.secret, "utf-8");
			String codeEncode = URLEncoder.encode(code, "utf-8");
			String url = String.format(Auth.AUTH_URL, GlobeApi.AUTH_POINT)+"?app_id="+key+"&app_secret="+secret+"&code="+codeEncode;
			this.__curlPost(url, null, handler);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
