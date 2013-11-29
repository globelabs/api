## Introduction

The Globe Short Message Service(SMS) API lets you develop client applications to communicate with people via text messaging. This document describes how to use the OAUTH/RESTful calling style and client library for Android to access and create Globe message data.

## Getting Started

The first thing we need to do is obtain an **APP ID** and **APP SECRET** from [Globe's Developer Website](http://developer.globelabs.com.ph/users/login). When visiting the link provided you should see a login form similar to *Figure Android.SMS.1*.

##### Figure Android.SMS.1 - Login
![Login Screen](https://raw.github.com/Openovate/rest-docs/master/sms/assets/login.jpg)
====

    Currently Globe's RESTful API is in BETA Mode. Ask your local Globe support to gain access to this BETA.

Next, login with your given credentials to get to the APP screen. When you get to the app screen scroll to the bottom right and click the "create" button to create a new app. You should see something familiar to *Figure Android.SMS.2*

##### Figure Android.SMS.2 - Create App Button
![Create App Button](https://raw.github.com/Openovate/rest-docs/master/sms/assets/create.jpg)
====

This will bring you to a form with required information that Globe will need to process your app creation. Fill out this form with the required fields at the very least and press Submit found at the bottom right in *Figure Android.SMS.3*.

##### Figure Android.SMS.3 - Create App Form
![Create App Form](https://raw.github.com/Openovate/rest-docs/master/sms/assets/form.jpg)
====

    **Important:** It's important that the Redirect URI and the Notify URI are using actual 
    URLs as in http://www.example.com/callback. Globe will call these URLs as described in the field.

From here you should be returned to the APP Detail Page in *Figure Android.SMS.4*. The important thing here is the **APP ID** and **APP SECRET**. These will be the information that you will need to manually set in the configurations when writing your application.

##### Figure Android.SMS.4 - App Details
![Create App Form](https://raw.github.com/Openovate/rest-docs/master/sms/assets/detail.jpg)

    **Note:** The data in this screen doesn't actually work. Please don't assume something went 
    wrong because you tried to use it.

## How to Include

First thing before you do any calls for Globe API using Android wrapper class is to include the base class called GlobeApi.

##### Figure Android.SMS.5 - Include Base Class

GlobeApi globe = new GlobeApi();

#####

## Authentication

Once we obtain the **APP ID** and **APP SECRET** we can begin to understand how the authentication works. Globe uses [OAUTH2](https://developers.google.com/accounts/docs/OAuth2), a common Android.SMScol to authenticate developers to use API Android.SMScols. To begin the authentication process you must redirect the user to a formatted URL using your **APP ID** and **APP SECRET** as in *Figure Android.SMS.6*.

##### Figure Android.SMS.6 - Invoke a Redirection

Now, initialize the `Auth` class inside GlobeApi and get the login URL using the `getLoginUrl` method.

    Auth oauth = globe.auth("[KEY]", "[SECRET");
    Strnig loginUrl = oauth.getLoginUrl();

Before invoking your redirect, please replace `[YOUR APP ID]` and `[YOUR APP SECRET]` in the figure above with your actual **APP ID** and **APP SECRET**. Based on what you inputed as your **Redirect URI** in your app details. Globe will authenticate permissions first with the user which should look like *Figure Android.SMS.7a* and *Figure Android.SMS.7b*.

##### Figure Android.SMS.7a - User Flow
![User Flow](https://raw.github.com/Openovate/rest-docs/master/sms/assets/user.jpg)
====
##### Figure Android.SMS.7b - Authorize
![Authorize](https://raw.github.com/Openovate/rest-docs/master/sms/assets/user.jpg)
====

Once the user gives permission, Globe will redirect the user to your Redirect URI with a `code` parameter appended to the end of it. This is how we recieve the code to continue the authentication process. *Figure Android.SMS.8* shows how this redirect will look like given that we set our redirect URI to `http://www.example.com/callback` in our app create form in *Figure Android.SMS.3*.

    **Important:** It is also possible that a user can give permissions to your app using just their 
    phone via SMS. Globe will call (not a phone call) your redirect URI with `access_token` and 
    `subscriber_number` appended to the end of it. From here you can process this request and 
    ignore the rest of the authentication process below.

##### Figure Android.SMS.8 - Redirected URI Sample

    http://www.example.com/callback?code=12345

`12345` in the URL figure above is what we need in order to get a more long lasting token for your app to use when making API calls. Everytime you make this call the `code` returned will be unique, so you should not hard code the `code` value in your application. The final step in the authentication process is about exchanging your `code` with a more permanent access token. We need to send Globe one final request shown in *Figure Android.SMS.9*

##### Figure Android.SMS.9 - Get the Access Token

Using the `Auth` object we initialized in **Figure Android.SMS.6**, we can get the access token using the script below.

    oauth.getAccessToken([CODE], new PostRequestHandler() {
        @Override
		public void postProcess(String string) {
		    //<string> is a json string containing the response of the server.
			Log.d("TEST ACCESS", string);
		}
    });

Before sending, please replace `[CODE]` in the figure above with the code given from *Figure Android.SMS.8*. 

Finally, Globe will return an access token you can use to start using the SMS API. **Figure Android.SMS.10** shows how this response will look like

##### Figure Android.SMS.10 - Access Token via JSON

    {
      "access_token": "GesiE2YhZlxB9VVMhv-PoI8RwNTsmX0D38g",
      "subscriber_number": "9051234567"'
    }

##

    **Note:** The data in above doesn't actually work. Please don't assume something went wrong 
    because you tried to use it.

## Sending

##### Figure Android.SMS.11 - Sample Send Message Request

First we need to initialize the `GlobeApi` class and then use that object to send SMS.

    GlobeApi globe = new GlobeApi();
    Sms sms = globe.sms([SHORT_CODE]);
    sms.sendMessage([access_token], [number], [message], new PostRequestHandler() {
        @Override
		public void postProcess(String string) {
		    //<string> is a json string containing the response of the server.
			Log.d("TEST ACCESS", string);
		}
    });

#####

    **Note:** You can get your Short Code value from your Globe App Details in `Figure Android.SMS.4. You also need to remove the `2158` digit in your short code.

##### Figure Android.SMS.12 - Sample Send Message Response
    
    {
      "outboundSMSMessageRequest": {
        "address": "09171234567",
        "deliveryInfoList": {
          "deliveryInfo": [],
          "resourceURL": null
        },
        "senderAddress": "1234",
        "outboundSMSTextMessage": {
          "message": "hello"
        },
        "reciptRequest": {
          "notifyURL": null,
          "callbackData": null,
          "senderName": null,
          "resourceURL": null
        }
      }
    }
