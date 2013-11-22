// Sample code of receiving code using Nodejs HTTP
// Use require('globe') if globe is on node_module folder
var globe = require('./../../lib/globeapi.js')(); // default application version is v1
var http = require('http');
var url = require('url'); // url parser

// Application Settings
var appShortCode = '<APP_SHORT_CODE>'; // full short code
var appId = '<APP_ID>'; // application id
var appSecret = '<APP_SECRET>'; // application secret

// Getting the login url
var auth = globe.Auth(appId, appSecret);

var callbackUrlPath = '<CALLBACK_URL_PATH>'; // sample: /callback
var newConnection = function(request, response) {
    // Manually parse the url to get the pathname and query request
    var urlParse = url.parse(request.url, true);
    request.pathname = urlParse.pathname;
    request.query = urlParse.query;

    var code = request.query['code'];

    if (request.pathname !== callbackUrlPath && request.method === 'GET') {
        response.writeHead(404); // Not Found
        response.end('File content not found!');
        return;
    } else if (!code) {
        response.writeHead(400); // Bad Request
        response.end('No code query!');
        return;
    }

    // Comment this line if you want to use the sample code of getting the access token
    // Sends the code as JSON
    response.end(JSON.stringify({
        'code' : code
    }, null, 4));

//    // Sample code of getting the access token
//    // Get the access token now using the code
//    auth.getAccessToken(code, function(req, res) {
//        var data = res.body;
//        // we assumed that the request is successful
//        if (res.statusCode === 200 && data && data['access_token']) {
//            // Get the access_token and subscriber number
//            var accessToken = data['access_token'];
//            var subscriberNumber = data['subscriber_number'];
//            console.log('Access Token:', accessToken);
//            console.log('Subscriber Number:', subscriberNumber);
//
//            // TODO If you want to add codes in here
//
//            // Sends the error
//            response.end(JSON.stringify(data, null, 4));
//        } else {
//            // Sends the error
//            response.end(JSON.stringify(data, null, 4));
//        }
//    });
};

app = http.createServer(newConnection);

app.listen(8080); // Starts the server and start listening
console.log('Try:', 'http://localhost:8080' + callbackUrlPath);