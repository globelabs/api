package com.globelabs.api;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		GlobeApi globe = new GlobeApi();
        
		Auth oauth = globe.auth("[KEY]", "[SECRET]");
		Log.d("Login", oauth.getLoginUrl());
		oauth.getAccessToken("[CODE]",
				new PostRequestHandler() {
					@Override
					public void postProcess(String string) {
						Log.d("TEST ACCESS", string);
					}
				});
		Sms sms = globe.sms("[SHORTCODE]");
		sms.sendMessage("[ACCESS_TOKEN]",
			"[RECEIVER_NUMBER]",
			"[MESSAGE]",
			new PostRequestHandler() {
				public void postProcess(String string) {
					//process the json response here
					Log.d("SEND SMS RESPONSE", string);
				}
			});

		Payment payment = globe.payment("[ACCESS_TOKEN]", "[RECEIVER_NUMBER]");
		payment.charge([AMOUNT], "[REFERENCE_CODE]", new PostRequestHandler() {
				@Override
				public void postProcess(String string) {
					//process the json response here
					Log.d("CHARGE RESPONSE", string);
				}
			}
		);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
