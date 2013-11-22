## Introduction

The Globe Short Message Service(SMS) API lets you develop client applications to communicate with people using the most basic form of electronic communication, text messaging. This document describes how to use the OAUTH/RESTful calling style and client libraries for various programming languages (currently Java, Python, PHP, NodeJS and Ruby) to access and create Globe message data. For other languages and libraries you can access the SMS protocol manually. The following documentation describes how you can access the SMS API directly with examples of request types, options and responses.

## Getting Started

The first thing we need to do is obtain an **APP ID** and **APP SECRET** from [Globe's Developer Website](http://developer.globelabs.com.ph/users/login). When visiting the link provided you should see a login form similar to *Figure PROTO.SMS.1*.

##### Figure PROTO.SMS.1 - Login
![Login Screen](https://raw.github.com/Openovate/rest-docs/master/sms/assets/login.jpg)
====

    Currently Globe's RESTful API is in BETA Mode. Ask your local Globe support to gain access to this BETA.

Next, login with your given credentials to get to the APP screen. When you get to the app screen scroll to the bottom right and click the "create" button to create a new app. You should see something familiar to *Figure PROTO.SMS.2*

##### Figure PROTO.SMS.2 - Create App Button
![Create App Button](https://raw.github.com/Openovate/rest-docs/master/sms/assets/create.jpg)
====

This will bring you to a form with required information that Globe will need to process your app creation. Fill out this form with the required fields at the very least and press Submit found at the bottom right in *Figure PROTO.SMS.3*.

##### Figure PROTO.SMS.3 - Create App Form
![Create App Form](https://raw.github.com/Openovate/rest-docs/master/sms/assets/form.jpg)
====

    **Important:** It's important that the Redirect URI and the Notify URI are using actual 
    URLs as in http://www.example.com/callback. Globe will call these URLs as described in the field.

From here you should be returned to the APP Detail Page in *Figure PROTO.SMS.4*. The important thing here is the **APP ID** and **APP SECRET**. These will be the information that you will need to manually set in the configurations when writing your application.

##### Figure PROTO.SMS.4 - App Details
![Create App Form](https://raw.github.com/Openovate/rest-docs/master/sms/assets/detail.jpg)

    **Note:** The data in this screen doesn't actually work. Please don't assume something went 
    wrong because you tried to use it.

## Authentication

Once we obtain the **APP ID** and **APP SECRET** we can begin to understand how the authentication works. Globe uses [OAUTH2](https://developers.google.com/accounts/docs/OAuth2), a common protocol to authenticate developers to use API protocols. To begin the authentication process you must redirect the user to a formatted URL using your **APP ID** as in *Figure PROTO.SMS.5*.

##### Figure PROTO.SMS.5 - Invoke a Redirection

    http://developer.globelabs.com.ph/dialog/oauth?app_id=[YOUR APP ID]

Before invoking your redirect, please replace `[YOUR APP ID]` in the figure above with your actual **APP ID**. Based on what you inputed as your **Redirect URI** in your app details. Globe will authenticate permissions first with the user which should look like *Figure PROTO.SMS.6a* and *Figure PROTO.SMS.6b*.

##### Figure PROTO.SMS.6a - User Flow
![User Flow](https://raw.github.com/Openovate/rest-docs/master/sms/assets/user.jpg)
====
##### Figure PROTO.SMS.6b - Authorize
![Authorize](https://raw.github.com/Openovate/rest-docs/master/sms/assets/user.jpg)
====

Once the user gives permission, Globe will redirect the user to your Redirect URI with a `code` parameter appended to the end of it. This is how we recieve the code to continue the authentication process. *Figure PROTO.SMS.7* shows how this redirect will look like given that we set our redirect URI to `http://www.example.com/callback` in our app create form in *Figure PROTO.SMS.3*.

    **Important:** It is also possible that a user can give permissions to your app using just their 
    phone via SMS. Globe will call (not a phone call) your redirect URI with `access_token` and 
    `subscriber_number` appended to the end of it. From here you can process this request and 
    ignore the rest of the authentication process below.

##### Figure PROTO.SMS.7 - Redirected URI Sample

    http://www.example.com/callback?code=12345

`12345` in the URL figure above is what we need in order to get a more long lasting token for your app to use when making API calls. Everytime you make this call the `code` returned will be unique, so you should not hard code the `code` value in your application. The final step in the authentication process is about exchanging your `code` with a more permanent access token. We need to send Globe one final request shown in *Figure PROTO.SMS.8*

##### Figure PROTO.SMS.8 - Get the Access Token

    POST http://developer.globelabs.com.ph/oauth/access_token?app_id=[YOUR APP ID]&app_secret=[YOUR APP SECRET]&code=[CODE]

Before sending, please replace `[YOUR APP ID]` in the figure above with your actual **APP ID**, replace `[YOUR APP SECRET]` in the figure above with your actual **APP SECRET** and replace `[CODE]` in the figure above with the code given from *Figure PROTO.SMS.7*. 

    **Note:** Remember set your access token request to globe using the POST method.

Finally, Globe will return an access token you can use to start using the SMS API. **Figure PROTO.SMS.9** shows how this response will look like

##### Figure PROTO.SMS.9 - Access Token via JSON

    {
      "access_token": "GesiE2YhZlxB9VVMhv-PoI8RwNTsmX0D38g",
      "subscriber_number": "9051234567"'
    }

##

    **Note:** The data in above doesn't actually work. Please don't assume something went wrong 
    because you tried to use it.

## Sending

To be able to use the SMS sending, you will need to send a POST Request.

**Request URL**

    http://devapi.globelabs.com.ph/smsmessaging/v1/outbound/[YOUR SHORT CODE]/requests?access_token=[YOUR ACCESS TOKEN]
    
**Parameters:**

| Field | Definition | Data Type |
--------|------------|-----------|
| address | is the subscriber MSISDN (mobile number). Parameter format can be 09xxxxxxxx | String or Integer |
| message | Must be URL-escaped as per RFC 1738. Currently limited to 160 character. | String


##### Figure PROTO.SMS.10 - Sample Send Message Request

    POST http://devapi.globelabs.com.ph/smsmessaging/v1/outbound/[YOUR SHORT CODE]/requests?access_token=[YOUR ACCESS TOKEN]
      -F "address=[MOBILE NUMBER]" \
      -F "message=[MESSAGE]" \
      
#####

    **Note:** You can get your Short Code value from your Globe App Details in `Figure PROTO.SMS.4. You also need to remove the `2158` digit in your short code.
      
##### Figure PROTO.SMS.11 - Sample Send Message Response

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

## Receiving

In receiving SMS, globe will send a data to your Notify URL (that you provided when you created your app) when the subscriber sends an SMS or replied to your short code number.

##### Figure PROTO.SMS.12 - Sample JSON Response

    {
        "registered_delivery" : "0",
        "command_length": "68",
        "source_addr_npi": "1",
        "service_type": "",
        "esm_class": "0",
        "validity_period": "",
        "command_status": "0",
        "message_payload[message]": "Get",
        "sequence_number": "14",
        "sm_default_msg_id": "0",
        "dest_addr_npi": "9",
        "source_addr_ton": "2",
        "source_network_type": "1",
        "priority_flag": "0",
        "dest_network_type": "1",
        "command": "deliver_sm",
        "replace_if_present_flag": "0",
        "source_addr": "9171234567",
        "destination_addr": "12345678",
        "dest_addr_ton": "4",
        "short_message[message]": "",
        "protocol_id": "0",
        "schedule_delivery_time": "",
        "data_coding": "0",
        "dest_addr_npi": "9",
        "command_id": "5"
    }


In your Notify URL, create a script that will catch and save these data to a file or to the database.
