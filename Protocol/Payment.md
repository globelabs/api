## Introduction

The Globe Payment API lets you develop client applications to charge people using the most basic form of electronic communication. This document describes how to use the OAUTH/RESTful calling style and client libraries for varrefuious programming languages (currently Java, Python, PHP, NodeJS and Ruby) to charge subscribers. For other languages and libraries you can access the Payment protocol manually. The following documentation describes how you can access the Payment API directly with examples of request types, options and responses.

## Getting Started

The first thing we need to do is obtain an **APP ID** and **APP SECRET** from [Globe's Developer Website](http://developer.globelabs.com.ph/users/login). When visiting the link provided you should see a login form similar to *Figure PROTO.1*.

##### Figure PROTO.1 - Login
![Login Screen](https://raw.github.com/Openovate/rest-docs/master/sms/assets/login.jpg)
====

    Currently Globe's RESTful API is in BETA Mode. Ask your local Globe support to gain access to this BETA.

Next, login with your given credentials to get to the APP screen. When you get to the app screen scroll to the bottom right and click the "create" button to create a new app. You should see something familiar to *Figure PROTO.2*

##### Figure PROTO.2 - Create App Button
![Create App Button](https://raw.github.com/Openovate/rest-docs/master/sms/assets/create.jpg)
====

This will bring you to a form with required information that Globe will need to process your app creation. Fill out this form with the required fields at the very least and press Submit found at the bottom right in *Figure PROTO.3*.

##### Figure PROTO.3 - Create App Form
![Create App Form](https://raw.github.com/Openovate/rest-docs/master/sms/assets/form.jpg)
====

    **Important:** It's important that the Redirect URI and the Notify URI are using actual 
    URLs as in http://www.example.com/callback. Globe will call these URLs as described in the field.

From here you should be returned to the APP Detail Page in *Figure PROTO.4*. The important thing here is the **APP ID** and **APP SECRET**. These will be the information that you will need to manually set in the configurations when writing your application.

##### Figure PROTO.4 - App Details
![Create App Form](https://raw.github.com/Openovate/rest-docs/master/sms/assets/detail.jpg)

    **Note:** The data in this screen doesn't actually work. Please don't assume something went 
    wrong because you tried to use it.

## Authentication

Once we obtain the **APP ID** and **APP SECRET** we can begin to understand how the authentication works. Globe uses [OAUTH2](https://developers.google.com/accounts/docs/OAuth2), a common protocol to authenticate developers to use API protocols. To begin the authentication process you must redirect the user to a formatted URL using your **APP ID** as in *Figure PROTO.5*.

##### Figure PROTO.5 - Invoke a Redirection

    http://developer.globelabs.com.ph/dialog/oauth?app_id=[YOUR APP ID]

Before invoking your redirect, please replace `[YOUR APP ID]` in the figure above with your actual **APP ID**. Based on what you inputed as your **Redirect URI** in your app details. Globe will authenticate permissions first with the user which should look like *Figure PROTO.6a* and *Figure PROTO.6b*.

##### Figure PROTO.6a - User Flow
![User Flow](https://raw.github.com/Openovate/rest-docs/master/sms/assets/user.jpg)
====
##### Figure PROTO.6b - Authorize
![Authorize](https://raw.github.com/Openovate/rest-docs/master/sms/assets/user.jpg)
====

Once the user gives permission, Globe will redirect the user to your Redirect URI with a `code` parameter appended to the end of it. This is how we recieve the code to continue the authentication process. *Figure PROTO.7* shows how this redirect will look like given that we set our redirect URI to `http://www.example.com/callback` in our app create form in *Figure PROTO.3*.

    **Important:** It is also possible that a user can give permissions to your app using just their 
    phone via SMS. Globe will call (not a phone call) your redirect URI with `access_token` and 
    `subscriber_number` appended to the end of it. From here you can process this request and 
    ignore the rest of the authentication process below.

##### Figure PROTO.7 - Redirected URI Sample

    http://www.example.com/callback?code=12345

`12345` in the URL figure above is what we need in order to get a more long lasting token for your app to use when making API calls. Everytime you make this call the `code` returned will be unique, so you should not hard code the `code` value in your application. The final step in the authentication process is about exchanging your `code` with a more permanent access token. We need to send Globe one final request shown in *Figure PROTO.8*

##### Figure PROTO.8 - Get the Access Token

    POST http://developer.globelabs.com.ph/oauth/access_token?app_id=[YOUR APP ID]&app_secret=[YOUR APP SECRET]&code=[CODE]

Before sending, please replace `[YOUR APP ID]` in the figure above with your actual **APP ID**, replace `[YOUR APP SECRET]` in the figure above with your actual **APP SECRET** and replace `[CODE]` in the figure above with the code given from *Figure PROTO.7*. 

    **Note:** Remember set your access token request to globe using the POST method.

Finally, Globe will return an access token you can use to start using the Charge API. **Figure PROTO.9** shows how this response will look like

##### Figure PROTO.9 - Access Token via JSON

    {
      "access_token": "GesiE2YhZlxB9VVMhv-PoI8RwNTsmX0D38g",
      "subscriber_number": "9051234567"'
    }

##

    **Note:** The data in above doesn't actually work. Please don't assume something went wrong 
    because you tried to use it.

## Charge

To use charge API you will need to send a POST request to the URL given below.

**Request URL**

    http://devapi.globelabs.com.ph/payment/v1/transactions/amount
    
**Parameters**

| Field | Definition | Data Type |
|-------|:----------:|:---------:|
| access_token | which contains security information for transacting with a subscriber. Subscriber needs to grant an app first via SMS or Web Form Subscriber Consent Workflow. | String |
| endUserId | is the MSISDN (mobile number) which you will charge to. Parameter format can be 09xxxxxxxx | String or Integer |
| amount | can be a whole number or decimal | String |
| referenceCode | (string, unique per charge event) is your reference for reconciliation purposes. The operator should include it in reports so that you can match their view of what has been sold with yours by matching the referenceCodes. | String or Integer |


##### Figure PROTO.10 - Sample Charge Request

    POST http://devapi.globelabs.com.ph/payment/v1/transactions/amount
      -F "access_token=[YOUR ACCESS TOKEN]" \
      -F "endUserId=[MOBILE NUMBER]" \
      -F "amount=[AMOUNT]" \
      -F "referenceCode=[UNIQUE REFERENCE]" \
      
##### Figure PROTO.11 - Sample Charge Response

    {
      "access_token": "GesiE2YhZlxB9VVMhv-PoI8RwNTsmX0D38g",
      "endUserId": "9171234567",
      "amount": "10",
      "referenceCode": "1234567",
      "success": true
    }
