package com.globelabs.api;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

public class Payment extends GlobeApi{
	protected String version;
	protected String toCharge;
	protected String token;
	protected String description;
	protected String clientCorrelator;
	
	public Payment(String version, String toCharge, String token) {
		this.version = version;
		this.toCharge = toCharge;
		this.token = token;
	}

	public void setVersion(String version) {
		this.version = version;
	}
	
	public String getToCharge() {
		return toCharge;
	}
	
	public void setToCharge(String toCharge) {
		this.toCharge = toCharge;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getClientCorrelator() {
		return clientCorrelator;
	}

	public void setClientCorrelator(String clientCorrelator) {
		this.clientCorrelator = clientCorrelator;
	}

	public void charge(float amount, String refNo, PostRequestHandler handler) {
		List<NameValuePair> parameters = new ArrayList<NameValuePair>();
		parameters.add(new BasicNameValuePair("endUserId", this.toCharge));
		parameters.add(new BasicNameValuePair("amount", String.format("%.2f", amount)));
		parameters.add(new BasicNameValuePair("description", this.description));
		parameters.add(new BasicNameValuePair("referenceCode", refNo));
		parameters.add(new BasicNameValuePair("transactionOperationStatus", "charged"));
		parameters.add(new BasicNameValuePair("access_token", this.token));
		String url = "https://%s/payment/%s/transactions/amount";
		url = String.format(url, GlobeApi.API_ENDPOINT, "v1");
		this.__curlPost(url, parameters, handler);
	}
}
