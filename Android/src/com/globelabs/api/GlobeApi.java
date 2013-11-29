package com.globelabs.api;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

public class GlobeApi {
    public static final String API_ENDPOINT = "devapi.globelabs.com.ph";
    public static final String AUTH_POINT = "developer.globelabs.com.ph";
    
	protected String version;

	public GlobeApi() {
		this.version = "v1";
	}
	
	public GlobeApi(String version) {
		this.version = version;
	}

	public Auth auth(String key, String secret) {
		return new Auth(key, secret);
	}
	
	public Payment payment(String token, String toCharge) {
		return new Payment("v1", toCharge, token);
	}
	
	public Payment payment(String token, String toCharge, String version) {
		return new Payment(version, toCharge, token);
	}
	
	public Sms sms(String shortCode) {
		return new Sms(shortCode, this.version);
	}
	
	public Sms sms(String shortCode, String version) {
		return new Sms(shortCode, version);
	}
	
	protected void __curlPost(String url, List<NameValuePair> fields, PostRequestHandler handler) {
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(url);
		try {
			if(fields!=null) {
				post.setEntity(new UrlEncodedFormEntity(fields));
			}
			HttpRequestAsyncTask task = new HttpRequestAsyncTask();
			task.setClient(client);
			task.setPost(post);
			task.setHandler(handler);
			task.execute();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
}
