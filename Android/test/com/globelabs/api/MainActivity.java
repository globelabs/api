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
        
		Auth oauth = globe.auth("97nG9SMy74jC7ocyn6i7xyCd5ny5SpXd", "fd64841f05085429f5b9ea91679fe62a7e2373afdfc6b3381685ecfb317819f1");
		Log.d("Login", oauth.getLoginUrl());
		oauth.getAccessToken("p6eUy4LKrh76xoGuAKKp9Ub75q5I5nA86Hrb9BjSKbrd7h49jMgHgMgXbIea9xkI4bgnLHKz5KLUzrB5RuokERLunR7p4HXr6ykFoaxRAS76KKdF9RzobCBra8RfA7cxbeia6zfazz5jCqXKokFkAx8bSMR6a8Fn77KeHbLEoXuMkBEKu7d5r4Up5gk8HGa9EqIAygndI8xjXGHBxraLhrA9XjS8eApaHap5RGIkRKbnUMgxz6uABL7Lh7zpRbUM",
				new PostRequestHandler() {
					@Override
					public void postProcess(String string) {
						Log.d("TEST ACCESS", string);
					}
				});
		Sms sms = globe.sms("4448");
		sms.sendMessage("X1obYywA-O5GRAEv4vEUrk-MvJF6P3uNkgLQS3roQUw",
			"9176234236",
			"message here",
			new PostRequestHandler() {
				public void postProcess(String string) {
					Log.d("TEST", string);
				}
			});

		Payment payment = globe.payment("X1obYywA-O5GRAEv4vEUrk-MvJF6P3uNkgLQS3roQUw", "9176234236");
		payment.charge(0, "44481000008", new PostRequestHandler() {
				@Override
				public void postProcess(String string) {
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
