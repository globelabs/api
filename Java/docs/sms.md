## Introduction

The Globe Short Message Service(SMS) API lets you develop client applications to communicate with people using the most basic form of electronic communication, text messaging. This document describes how to use the OAUTH/RESTful calling style and client libraries for various programming languages (currently Java, Python, PHP, NodeJS and Ruby) to access and create Globe message data. For other languages and libraries you can access the SMS protocol manually. The following documentation describes how you can access the SMS API directly with examples of request types, options and responses.

## Getting Started

The first thing we need to do is obtain an **APP ID**, **APP SECRET**, and **SHORTCODE** from [Globe's Developer Website](http://developer.globelabs.com.ph/users/login). When visiting the link provided you should see a login form similar to *Figure PROTO.1*.

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

From here you should be returned to the APP Detail Page in *Figure PROTO.4*. You will need the **APP ID**, **APP SECRET**, and the **SHORTCODE** in writing your application.

##### Figure PROTO.4 - App Details
![Create App Form](https://raw.github.com/Openovate/rest-docs/master/sms/assets/detail.jpg)

    **Note:** The data in this screen doesn't actually work. Please don't assume something went 
    wrong because you tried to use it.

## How to Include

##### Figure PROTO.5 - Include necessary JAR files in your lib and build path

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

Once we obtain the **APP ID** and **APP SECRET** we can begin to understand how the authentication works. Globe uses [OAUTH2](https://developers.google.com/accounts/docs/OAuth2), a common protocol to authenticate developers to use API protocols. To begin the authentication process you must redirect the user to a formatted URL using your **APP ID** as in *Figure PROTO.6*.

##### Figure PROTO.6 - Invoke a Redirection

Now, initialize the `Auth` class and get the login URL using the `getLoginUrl` method.

    Auth auth = new Auth();
    String loginUrl = auth.getLoginUrl([APP_ID]);

Before invoking your redirect, please replace `[YOUR APP ID]` in the figure above with your actual **APP ID**. Globe will authenticate permissions first with the user which should look like *Figure PROTO.7a* and *Figure PROTO.7b*.

##### Figure PROTO.7a - User Flow
![User Flow](https://raw.github.com/Openovate/rest-docs/master/sms/assets/user.jpg)
====
##### Figure PROTO.7b - Authorize
![Authorize](https://raw.github.com/Openovate/rest-docs/master/sms/assets/user.jpg)
====

Once the user gives permission, Globe will redirect the user to your **Redirect URI** with a `code` parameter appended to the end of it. This is how we receive the code to continue the authentication process. *Figure PROTO.8* shows how this redirect will look like given that we set our redirect URI to `http://www.example.com/callback` in our app create form in *Figure PROTO.3*.

    **Important:** It is also possible that a user can give permissions to your app using just their 
    phone via SMS. Globe will call (not a phone call) your redirect URI with `access_token` and 
    `subscriber_number` appended to the end of it. From here you can process this request and 
    ignore the rest of the authentication process below.

##### Figure PROTO.8 - Redirected URI Sample

    http://www.example.com/callback?code=12345

`12345` in the URL figure above is what we need in order to get a more long lasting token for your app to use when making API calls. Everytime you make this call the `code` returned will be unique, so you should not hard code the `code` value in your application. The final step in the authentication process is about exchanging your `code` with a more permanent access token. We need to send Globe one final request shown in *Figure PROTO.9*

##### Figure PROTO.9 - Get the Access Token

Using the `Auth` object we initialized in **Figure PROTO.6**, we can get the access token using the script below.

    AccessTokenResponse response = auth.getAccessToken([APP_ID], [APP_SECRET], [CODE]);
    
Before sending, please replace `[APP_ID]`, `[APP_SECRET]`, and `[CODE]` in the figure above with the your respective given app ID, app secret, and the code given from Figure PROTO.8.

Finally, Globe will return an access token you can use to start using the Charge API. **Figure PROTO.10** shows how this response will look like

##### Figure PROTO.10 - Access Token Response

    AccessTokenResponse [accessToken=_Ak28sdfl32r908sdf0q843qjlkjdf90234jlkasd98, subscriberNumber=9173849494] Response [responseCode=200, responseMessage=OK, statusLine=HTTP/1.1 200 OK, content={"subscriber_number":"9173849494","access_token":"_Ak28sdfl32r908sdf0q843qjlkjdf90234jlkasd98"}]

##

    **Note:** The data above doesn't actually work. Please don't assume something went wrong 
    because you tried to use it.


## Sending an SMS to a subscriber

##### Figure PROTO.11 - Sample Send Message Request

    Sms sms = new Sms([SHORTCODE]);
    sms.sendMessage([SUBSCRIBER_NUMBER], [ACCESS_TOKEN], [MESSAGE]);

**Parameters**

| Parameters | Definition | Data Type |
|-------|:----------:|:---------:|
| [SHORTCODE] | the last 4 digits of your shortcode. 2158XXXX. | String |
| [ACCESS_TOKEN] | which contains security information for transacting with a subscriber. Subscriber needs to grant an app first via SMS or Web Form Subscriber Consent Workflow. | String |
| [SUBSCRIBER_NUMBER] | is the 10-digit MSISDN (mobile number) which you will charge to. Parameter format can be `9xxxxxxxx` | String |
| [MESSAGE] | the SMS body. Must be 160 characters or less. | String |


##### Figure PROTO.12 - Sample Send Message Response

    SendSmsResponse [success=true, message=Hello World, address=9173849494, senderAddress=9999, accessToken=_Ak28sdfl32r908sdf0q843qjlkjdf90234jlkasd98] Response [responseCode=201, responseMessage=Created, statusLine=HTTP/1.1 201 Created, content={"message":"Hello World","senderAddress":"9999","address":"9173849494","success":"true","access_token":"_Ak28sdfl32r908sdf0q843qjlkjdf90234jlkasd98"}]


## Parsing a Received SMS

##### Figure PROTO.11 - Sample Get Message Request

    Sms sms = new Sms([SHORTCODE]);
    sms.getMessage([RAW_BODY]);

**Parameters**

| Parameters | Definition | Data Type |
|-------|:----------:|:---------:|
| [RAW_BODY] | the raw body sent by the Globe Server to the notify URL. | String |

Sample raw body

    command_length=71&command_id=5&command_status=0&sequence_number=70&command=deliver_sm&service_type=&source_addr_ton=2&source_addr_npi=1&source_addr=9173849494&dest_addr_ton=4&dest_addr_npi=9&destination_addr=21589999&esm_class=0&protocol_id=0&priority_flag=0&schedule_delivery_time=&validity_period=&registered_delivery=0&replace_if_present_flag=0&data_coding=0&sm_default_msg_id=0&short_message[message]=&source_network_type=1&dest_network_type=1&message_payload[message]=A%20%20B%20C%20D%20.E

##### Figure PROTO.12 - Sample Send Message Response

    SmsResponse [sourceAddr=9173849494, destinationAddr=21589999, message=A  B C D .E]
