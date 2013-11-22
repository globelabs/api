// Sample code of receiving message from GlobeLabs Callback URI using Express Framework
var express = require('express'); // express framework: npm install express

// Express Settings
var app = express();
app.use(express.bodyParser());

var callbackUrlPath = '<NOTIFY_URL_PATH>'; // sample: /notify
app.post(callbackUrlPath, function(request, response, next) {
    // Express Framework automatically parse the post data

    // Sends the data as JSON
    response.end(JSON.stringify(request.body, null, 4));
});

app.listen(8080); // Starts the server and start listening
console.log('Try:', 'http://localhost:8080' + callbackUrlPath);