## Introduction

The Globe Payment API lets you develop client applications to charge subscribers. This document describes how to use the OAUTH/RESTful calling style and client library for Java. For other languages and library you can access the Payment protocol manually. The following documentation describes how you can access the Payment API directly with examples of request types, options and responses.

You may also check out the javadocs at Java/javadocs.

## Getting Started

The first thing we need to do is obtain an **APP ID**, **APP SECRET**, and **SHORTCODE** from [Globe's Developer Website](http://developer.globelabs.com.ph/users/login). When visiting the link provided you should see a login form similar to *Figure JAVA.PAYMENT.1*.

##### Figure JAVA.PAYMENT.1 - Login
![Login Screen](https://raw.github.com/Openovate/rest-docs/master/sms/assets/login.jpg)
====

    Currently Globe's RESTful API is in BETA Mode. Ask your local Globe support to gain access to this BETA.

Next, login with your given credentials to get to the APP screen. When you get to the app screen scroll to the bottom right and click the "create" button to create a new app. You should see something familiar to *Figure JAVA.PAYMENT.2*

##### Figure JAVA.PAYMENT.2 - Create App Button
![Create App Button](https://raw.github.com/Openovate/rest-docs/master/sms/assets/create.jpg)
====

This will bring you to a form with required information that Globe will need to process your app creation. Fill out this form with the required fields at the very least and press Submit found at the bottom right in *Figure JAVA.PAYMENT.3*.

##### Figure JAVA.PAYMENT.3 - Create App Form
![Create App Form](https://raw.github.com/Openovate/rest-docs/master/sms/assets/form.jpg)
====

    **Important:** It's important that the Redirect URI and the Notify URI are using actual 
    URLs as in http://www.example.com/callback. Globe will call these URLs as described in the field.

From here you should be returned to the APP Detail Page in *Figure JAVA.PAYMENT.4*. You will need the **APP ID**, **APP SECRET**, and the **SHORTCODE** in writing your application.

##### Figure JAVA.PAYMENT.4 - App Details
![Create App Form](https://raw.github.com/Openovate/rest-docs/master/sms/assets/detail.jpg)

    **Note:** The data in this screen doesn't actually work. Please don't assume something went 
    wrong because you tried to use it.

## How to Include

##### Figure JAVA.PAYMENT.5 - Include necessary JAR files in your lib and build path

    **Note:** You can find the necessary JAR files inside the Java/lib/ folder.

    Main JAR file: globe-api-[version]-bin.jar

    Dependencies include the following JARs:

    1. Apache Commons IO 2.4 - commons-io-2.4.jar
    2. Apache Commons Logging 1.1.3 - commons-logging-1.1.3.jar
    3. Apache HttpClient 4.3.1 - httpclient-4.3.1.jar
    4. Apache HttpCore 4.3 - httpcore-4.3.jar
    5. JSON (JavaScript Object Notation) - json-20090211.jar

    You may also include these dependencies via Maven if available.

## Authentication

Once we obtain the **APP ID** and **APP SECRET** we can begin to understand how the authentication works. Globe uses [OAUTH2](https://developers.google.com/accounts/docs/OAuth2), a common protocol to authenticate developers to use API protocols. To begin the authentication process you must redirect the user to a formatted URL using your **APP ID** as in *Figure JAVA.PAYMENT.6*.

##### Figure JAVA.PAYMENT.6 - Invoke a Redirection

Now, initialize the `Auth` class and get the login URL using the `getLoginUrl` method.

    Auth auth = new Auth();
    String loginUrl = auth.getLoginUrl([APP_ID]);

Before invoking your redirect, please replace `[YOUR APP ID]` in the figure above with your actual **APP ID**. Globe will authenticate permissions first with the user which should look like *Figure JAVA.PAYMENT.7a* and *Figure JAVA.PAYMENT.7b*.

##### Figure JAVA.PAYMENT.7a - User Flow
![User Flow](https://raw.github.com/Openovate/rest-docs/master/sms/assets/user.jpg)
====
##### Figure JAVA.PAYMENT.7b - Authorize
![Authorize](https://raw.github.com/Openovate/rest-docs/master/sms/assets/user.jpg)
====

Once the user gives permission, Globe will redirect the user to your **Redirect URI** with a `code` parameter appended to the end of it. This is how we receive the code to continue the authentication process. *Figure JAVA.PAYMENT.8* shows how this redirect will look like given that we set our redirect URI to `http://www.example.com/callback` in our app create form in *Figure JAVA.PAYMENT.3*.

    **Important:** It is also possible that a user can give permissions to your app using just their 
    phone via SMS. Globe will call (not a phone call) your redirect URI with `access_token` and 
    `subscriber_number` appended to the end of it. From here you can process this request and 
    ignore the rest of the authentication process below.

##### Figure JAVA.PAYMENT.8 - Redirected URI Sample

    http://www.example.com/callback?code=12345

`12345` in the URL figure above is what we need in order to get a more long lasting token for your app to use when making API calls. Everytime you make this call the `code` returned will be unique, so you should not hard code the `code` value in your application. The final step in the authentication process is about exchanging your `code` with a more permanent access token. We need to send Globe one final request shown in *Figure JAVA.PAYMENT.9*

##### Figure JAVA.PAYMENT.9 - Get the Access Token

Using the `Auth` object we initialized in **Figure JAVA.PAYMENT.6**, we can get the access token using the script below.

    AccessTokenResponse response = auth.getAccessToken([APP_ID], [APP_SECRET], [CODE]);
    
Before sending, please replace `[APP_ID]`, `[APP_SECRET]`, and `[CODE]` in the figure above with the your respective given app ID, app secret, and the code given from Figure JAVA.PAYMENT.8.

Finally, Globe will return an access token you can use to start using the Charge API. **Figure JAVA.PAYMENT.10** shows how this response will look like

##### Figure JAVA.PAYMENT.10 - Access Token Response

    AccessTokenResponse [accessToken=_Ak28sdfl32r908sdf0q843qjlkjdf90234jlkasd98, subscriberNumber=9173849494] Response [responseCode=200, responseMessage=OK, statusLine=HTTP/1.1 200 OK, content={"subscriber_number":"9173849494","access_token":"_Ak28sdfl32r908sdf0q843qjlkjdf90234jlkasd98"}]

##

    **Note:** The data above doesn't actually work. Please don't assume something went wrong 
    because you tried to use it.


## Charge a User

To use the Payment API you will need to send a POST request to the URL given below.

**Request URL**

    http://devapi.globelabs.com.ph/payment/v1/transactions/amount
    
**Parameters**

| Parameters | Definition | Data Type |
|-------|:----------:|:---------:|
| [ACCESS_TOKEN] | which contains security information for transacting with a subscriber. Subscriber needs to grant an app first via SMS or Web Form Subscriber Consent Workflow. | String |
| [SUBSCRIBER_NUMBER] | is the 10-digit MSISDN (mobile number) which you will charge to. Parameter format can be `9xxxxxxxx` | String |
| [AMOUNT] | amount to be charged to the subscriber | BigDecimal |
| [REFERENCE_NUMBER] | a unique transaction ID with a format of `[SHORTCODE_WITHOUT_2158]`+`#######` where `#######` is an incremented number beginning from `1000001`. | (numeric) String |


##### Figure JAVA.PAYMENT.11 - Sample Charge Request

    Payment payment = new Payment([SUBSCRIBER_NUMBER], [ACCESS_TOKEN]);
    payment.charge([AMOUNT], [REFERENCE_NUMBER]);
    
    **Note:** You can get your Short Code value from your Globe App Details in `Figure JAVA.PAYMENT.4. You also need to remove the `2158` digit in your short code.
      
##### Figure JAVA.PAYMENT.12 - Sample Charge Response

    ChargeUserResponse [success=true, amount=10.00, subscriberNumber=9173849494, referenceCode=99991000001, accessToken=_Ak28sdfl32r908sdf0q843qjlkjdf90234jlkasd98] Response [responseCode=201, responseMessage=Created, statusLine=HTTP/1.1 201 Created, content={"amount":"10","endUserId":"9173849494","referenceCode":"REF-12345","success":"true","access_token":"_Ak28sdfl32r908sdf0q843qjlkjdf90234jlkasd98"}]
