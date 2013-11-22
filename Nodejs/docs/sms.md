## Introduction

The Globe Short Message Service(SMS) API lets you develop client applications to communicate with people using the most basic form of electronic communication, text messaging. This document describes how to use the OAUTH/RESTful calling style and client library using NodeJS to access and create Globe message data. For other languages and libraries you can access the SMS protocol manually. The following documentation describes how you can access the SMS API directly with examples of request types, options and responses.

## Getting Started

The first thing we need to do is obtain an **APP ID** and **APP SECRET** from [Globe's Developer Website](http://developer.globelabs.com.ph/users/login). When visiting the link provided you should see a login form similar to *Figure NODEJS.SMS.1*.

##### Figure NODEJS.SMS.1 - Login
![Login Screen](https://raw.github.com/Openovate/rest-docs/master/sms/assets/login.jpg)
====

    Currently Globe's RESTful API is in BETA Mode. Ask your local Globe support to gain access to this BETA.

Next, login with your given credentials to get to the APP screen. When you get to the app screen scroll to the bottom right and click the "create" button to create a new app. You should see something familiar to *Figure NODEJS.SMS.2*

##### Figure NODEJS.SMS.2 - Create App Button
![Create App Button](https://raw.github.com/Openovate/rest-docs/master/sms/assets/create.jpg)
====

This will bring you to a form with required information that Globe will need to process your app creation. Fill out this form with the required fields at the very least and press Submit found at the bottom right in *Figure NODEJS.SMS.3*.

##### Figure NODEJS.SMS.3 - Create App Form
![Create App Form](https://raw.github.com/Openovate/rest-docs/master/sms/assets/form.jpg)
====

    **Important:** It's important that the Redirect URI and the Notify URI are using actual 
    URLs as in http://www.example.com/callback. Globe will call these URLs as described in the field.

From here you should be returned to the APP Detail Page in *Figure NODEJS.SMS.4*. The important thing here is the **APP ID** and **APP SECRET**. These will be the information that you will need to manually set in the configurations when writing your application.

##### Figure NODEJS.SMS.4 - App Details
![Create App Form](https://raw.github.com/Openovate/rest-docs/master/sms/assets/detail.jpg)

    **Note:** The data in this screen doesn't actually work. Please don't assume something went 
    wrong because you tried to use it.

## How to Include

First thing before you do any calls for Globe API using Node.JS wrapper class is to include main class called globelabs.

##### Figure NODEJS.SMS.5 - Include Main Class

**Note:** To include these you have to place the Globe API Node.JS inside the node_modules and name it to globelabs.

    myapp
    - node_module
      - globelabs
        - [nodejs files]
      - [other libraries you will use]
    - [your application files]

Now, you can include the GlobeAPI Node.JS.

    var globe = require('globelabs')();

## Authentication

Once we obtain the **APP ID** and **APP SECRET** we can begin to understand how the authentication works. Globe uses [OAUTH2](https://developers.google.com/accounts/docs/OAuth2), a common protocol to authenticate developers to use API protocols. To begin the authentication process you must redirect the user to a formatted URL using your **APP ID** and **APP SECRET** as in *Figure NODEJS.SMS.6*.

##### Figure NODEJS.SMS.6 - Invoke a Redirection

Now, initialize the `Auth` class inside globe and get the login URL using the `getLoginUrl` method.

    var auth = globe.Auth([APP_ID], [APP_SECRET]);
    var loginUrl = auth.getLoginUrl();
**Note**: The variable globe we use in here is initialize on *Figure NODEJS.SMS.5*.

Before invoking your redirect, please replace `[YOUR APP ID]` and `[YOUR APP SECRET]` in the figure above with your actual **APP ID** and **APP SECRET**. Based on what you inputed as your **Redirect URI** in your app details. Globe will authenticate permissions first with the user which should look like *Figure NODEJS.SMS.7a* and *Figure NODEJS.SMS.7b*.

##### Figure NODEJS.SMS.7a - User Flow
![User Flow](https://raw.github.com/Openovate/rest-docs/master/sms/assets/user.jpg)
====
##### Figure NODEJS.SMS.7b - Authorize
![Authorize](https://raw.github.com/Openovate/rest-docs/master/sms/assets/user.jpg)
====

Once the user gives permission, Globe will redirect the user to your Redirect URI with a `code` parameter appended to the end of it. This is how we recieve the code to continue the authentication process. *Figure NODEJS.SMS.8* shows how this redirect will look like given that we set our redirect URI to `http://www.example.com/callback` in our app create form in *Figure NODEJS.SMS.3*.

    **Important:** It is also possible that a user can give permissions to your app using just their 
    phone via SMS. Globe will call (not a phone call) your redirect URI with `access_token` and 
    `subscriber_number` appended to the end of it. From here you can process this request and 
    ignore the rest of the authentication process below.

##### Figure NODEJS.SMS.8 - Redirected URI Sample

    http://www.example.com/callback?code=12345

`12345` in the URL figure above is what we need in order to get a more long lasting token for your app to use when making API calls. Everytime you make this call the `code` returned will be unique, so you should not hard code the `code` value in your application. The final step in the authentication process is about exchanging your `code` with a more permanent access token. We need to send Globe one final request shown in *Figure NODEJS.SMS.9*

##### Figure NODEJS.SMS.9 - Get the Access Token

Using the `Auth` object we initialized in **Figure NODEJS.SMS.6**, we can get the access token using the script below.
Callback functions will received 3 parameters. *http.ClientRequest* and *http.ServerResponse*. *Please see (http://nodejs.org/api/http.html) for documentation of http.*

    var callback = function(request, response) {};

    auth.getAccessToken([CODE], callback);
**Note**: Please replace the `callback` function with your own callback function. The callback function above is just an example.

Before sending, please replace `[CODE]` in the figure above with the code given from **Figure NODEJS.SMS.8**.

Finally, Globe will return an access token you can use to start using the Charge API. **Figure NODEJS.SMS.10** shows how this response will look like

##### Figure NODEJS.SMS.10 - Access Token via JSON

Inside your callback function, *Figure NODEJS.SMS.9*, the request object will automatically parse the received data from the server. To get the parse body received from the server, use this call `response.body`.

We assumed that the request is successful.

    var callback = function(request, response) {
        var body = response.body; // get the response body
        console.log('Access Token:', body['access_token']);
        console.log('Subscriber Number:', body['subscriber_number']);
    };
    
    // The output above code
    // Access Token: GesiE2YhZlxB9VVMhv-PoI8RwNTsmX0D38g
    // Subscriber Number: 9051234567

You notice that the `access_token` and `subscriber_number` is already JSON Object.

Not all request always success if the server failed to validate the code you request. How can you get the error message? Good news! The callback function also received an error message. See sample code below.

We assumed that the request fails.

    var callback = function(request, response) {
        var body = response.body; // get the response body
        console.log('Error Message:', body['error']);
    };
    
    // The output above code
    // Error Message: <The error message thrown by the server>

##

## Sending

To use charge API you will need to send a POST request to the URL given below.

**Request URL**

    http://devapi.globelabs.com.ph/smsmessaging/v1/outbound/[SHORT_CODE_WITHOUT_2158]/requests

**Note**: The `[SHORT_CODE_WITHOUT_2158]` is the path of your application. For example the short code's of your application is 21581234, your the real path of your application is 1234.

**Parameters**

| Parameters | Definition | Data Type |
|-------|:----------:|:---------:|
| [SHORT_CODE] | your application's short code. | String or Integer |
| [SUBSCRIBER_NUMBER] | is the MSISDN (mobile number) which you will charge to. Parameter format can be 9xxxxxxxx | String or Integer |
| [YOUR_ACCESS_TOKEN] | which contains security information for transacting with a subscriber. Subscriber needs to grant an app first via SMS or Web Form Subscriber Consent Workflow. | String |
| [MESSAGE] | the message you want to send. | String |
| [YOUR_CALLBACK_FUNCTION] | is the function who will be trigger if the request is completed. | Function |

##### Figure NODEJS.SMS.11 - Sample Send Message Request

    var sms = globe.SMS([SHORT_CODE], [SUBSCRIBER_NUMBER], [YOUR_ACCESS_TOKEN]);
    sms.sendMessage([MESSAGE], [YOUR_CALLBACK_FUNCTION]);

**Note**: Please see *Figure NODEJS.SMS.10* for the explanation of `[YOUR_CALLBACK_FUNCTION]`.;

##### Figure NODEJS.12 - Sample Send Message Response

We assumed that we already sent the message to the user and we are receiving the server response.

    var callback = function(request, response) {
        var body = response.body;
        console.log(body);
        
        // Output above code
        // {
        //     access_token: "GesiE2YhZlxB9VVMhv-PoI8RwNTsmX0D38g",
        //     address: "9171234567",
        //     amount: "10",
        //     senderAddress: "1234",
        //     success: true
        // }
    };
