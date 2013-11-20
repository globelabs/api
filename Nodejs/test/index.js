// Use require('globe') if globe is on node_module folder
var globe = require('./../lib/index.js')(); // default application version is v1

// Application Settings
var appShortCode = '<APP_SHORT_CODE>'; // full short code
var appId = '<APP_ID>'; // application id
var appSecret = '<APP_SECRET>'; // application secret

// Getting the login url
var auth = globe.Auth(appId, appSecret);
//auth.setLogger(console); // uncomment to log request
console.log('Login URL:', auth.getLoginUrl()); // Prints out the login url

// Getting the access token and subscriber number using the code received by the app's redirect uri
var code = '<CODE>'; // code received from app's redirect uri
auth.getAccessToken(code, function(req, res, data) {
    console.log('Code Response:', data);

    // we assumed that the request is successful
    if (res.statusCode === 200 && data && data['access_token']) {
        // Get the access_token and subscriber number
        var accessToken = data['access_token'];
        var subscriberNumber = data['subscriber_number'];

        // Build up SMS
        var sms = globe.SMS(appShortCode, subscriberNumber, accessToken);
//        sms.setLogger(console); // uncomment to log request

        // Sends a message
        var message = 'Hello World ' + new Date().toISOString(); // set your custom message here
        sms.sendMessage(message, function(req, res, data) {
            console.log('SMS Response:', data);
        });

        // Build up Payment
        var payment = globe.Payment(subscriberNumber, accessToken);
//        payment.setLogger(console); // uncomment to log request
        var refCode = new Date().getTime().toString(); // ref code
        var amount = '0'; // set amount to 0 as charge amount

        // Charge the subscriber
        payment.charge(refCode, amount, function(req, res, data) {
            console.log('Payment Response:', data);
        });
    }
});