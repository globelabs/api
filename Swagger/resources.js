var sw = require('swagger-node-express');
var param = sw.params;
var url = require('url');
var swe = sw.errors;
var httpRequest = require('./request.js')()('http://devapi.globelabs.com.ph');

var header = {
    'Content-Type' : 'application/json'
};

exports.sendSMS = {
    'spec' : {
        'description' : 'TODO',
        'path' : '/smsmessaging/v1/outbound/{senderAddress}/requests',
        'notes' : 'TODO',
        'summary' : 'Send SMS',
        'method' : 'POST',
        'params' : [
            param.path('senderAddress', 'The short code of your app w/o the starting 2158 number.', 'int'),
            param.query('access_token', 'Subscribers access token', 'string', true),
            param.form('message', 'The message', 'string'),
            param.form('address', 'The subscriber number who owns the access token', 'string'),
            param.form('clientCorrelator', 'Unique identification of string to avoid multiple sending', 'string')
        ],
        'errorResponses' : [{ 'code' : 'TODO', 'reason' : 'TODO' }],
        'nickname' : 'sendSMS'
    },
    'action' : function(req, res) {
        var urlParse = url.parse(req.url, true);
        var post = JSON.stringify(req.body);

        httpRequest.post(urlParse.pathname, urlParse.query, post, header, function(request, response, data) {
            res.writeHead(response.statusCode);
            res.end(JSON.stringify(data));
        });
    }
};

exports.chargeClient = {
    'spec' : {
        'description' : 'TODO',
        'path' : '/payment/v1/transactions/amount',
        'notes' : 'TODO',
        'summary' : 'Charge Client',
        'method' : 'POST',
        'params' : [
            param.query('access_token', 'Subscribers access token', 'string', true),
            param.form('endUserId', 'Subscriber\'s number', 'string'),
            param.form('referenceCode', 'Unique client identifier to avoid multiple charge', 'string', new Date().getTime().toString()),
            param.form('amount', 'The amount', 'string', '1'),
            param.form('clientCorrelator', 'Unique identification of string to avoid multiple charging to user', 'string')
        ],
        'errorResponses' : [{ 'code' : 'TODO', 'reason' : 'TODO' }],
        'nickname' : 'chargeClient'
    },
    'action' : function(req, res) {
        var urlParse = url.parse(req.url, true);
        var post = JSON.stringify(req.body);

        httpRequest.post(urlParse.pathname, urlParse.query, post, header, function(request, response, data) {
            res.writeHead(response.statusCode);
            res.end(JSON.stringify(data));
        });
    }
};