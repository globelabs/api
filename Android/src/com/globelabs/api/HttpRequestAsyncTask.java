package com.globelabs.api;

import java.io.IOException;
import java.lang.reflect.Method;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.util.EntityUtils;

import android.os.AsyncTask;

public class HttpRequestAsyncTask extends AsyncTask<String, Void, String> {
	HttpClient client;
	HttpPost post;
	HttpGet get;
	String responseString="";
	Method method;
	PostRequestHandler handler;
	
	public void setHandler(PostRequestHandler handler) {
		this.handler = handler;
	};
	
	public void setClient(HttpClient client) {
		this.client = client;
	}
	
	public void setPost(HttpPost post) {
		this.post = post;
	}
	
	public void setGet(HttpGet get) {
		this.get = get;
	}
	
	public void setMethod(Method method) {
		this.method = method;
	}
	
	@Override
	protected String doInBackground(String... arg0) {
		try {
			HttpResponse responseGet = null;
			if(post!=null) {
				responseGet = client.execute(post);
			} else if (get!=null) {
				responseGet = client.execute(get);
			}
			HttpEntity resEntityGet = responseGet.getEntity();  
			if (resEntityGet != null) {  
			    responseString = EntityUtils.toString(resEntityGet);
			    return responseString;
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
    protected void onPostExecute(String response) {
		handler.postProcess(response);
    }
}
