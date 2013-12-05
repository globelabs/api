package com.globelabs.api;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

public class Sms extends GlobeApi{
	public static final String CURL_URL = "http://%s/smsmessaging/%s/outbound/%s/";
	
	protected String version;
	protected String address;
	
	public Sms() {}
	
	public Sms(String address) {
		this.address = address;
	}
	
	public Sms(String address, String version) {
		this.address = address;
		this.version = version;
	}
	
	public void getMessages(PostRequestHandler handler) {
		String url = "http://%s/smsmessaging/%s/inbound/registrations/%s/messages";
		url = String.format(url, GlobeApi.API_ENDPOINT, "v1", this.address);
		this.__curlGet(url, handler);
	}
	
	public void sendMessage(String accessToken, String sendTo, String message, PostRequestHandler handler) {
		List<NameValuePair> parameters = new ArrayList<NameValuePair>();
		parameters.add(new BasicNameValuePair("access_token", accessToken));
		parameters.add(new BasicNameValuePair("address", sendTo));
		parameters.add(new BasicNameValuePair("message", message));
		parameters.add(new BasicNameValuePair("senderAddress", this.address));
		String url = "http://%s/smsmessaging/%s/outbound/%s/requests";
		url = String.format(url, GlobeApi.API_ENDPOINT, "v1", this.address);
		this.__curlPost(url, parameters, handler);
	}
}
