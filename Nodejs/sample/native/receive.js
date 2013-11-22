// Sample code of receiving message using Nodejs HTTP
var http = require('http');
var url = require('url'); // url parser
var querystring = require('querystring'); // query/post string parser

//var callbackUrlPath = '<NOTIFY_URL_PATH>'; // sample: /notify
var newConnection = function(request, response) {
    // Manually parse the url to get the pathname and query request
    var urlParse = url.parse(request.url, true);
    request.pathname = urlParse.pathname;
    request.query = urlParse.query;

    if (request.pathname !== callbackUrlPath && request.method === 'POST') {
        response.writeHead(404); // Not Found
        response.end('File content not found!');
        return;
    }

    var data = null;
    // event handler for received data
    request.on('data', function(chunk) {
        // if buffer is null set it to empty string ''
        if (data === null) {
            data = '';
        }

        // append chunk to data
        data += chunk;
    });

    request.on('end', function() {
        // if no data received
        if (data === null) {
            response.writeHead(400); // Bad Request
            response.end('No data received!');
            return;
        }

        // We parse the buffer
        request.body = querystring.parse(data);

        // Sends the data as JSON
        response.end(JSON.stringify(request.body, null, 4));
    });
};

app = http.createServer(newConnection);

app.listen(8080); // Starts the server and start listening
console.log('Try:', 'http://localhost:8080' + callbackUrlPath);