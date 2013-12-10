var sw = require('swagger-node-express');
var param = sw.params;
var url = require('url');
var swe = sw.errors;
var httpRequest = require('./request.js')()('http://devapi.globelabs.com.ph');

exports.sendSMS = {
    'spec' : {
        'description' : 'Send and receive SMS messages',
        'path' : '/smsmessaging/v1/outbound/{senderAddress}/requests',
        'notes' : 'Send an SMS message to one or more mobile terminals',
        'summary' : 'Send SMS',
        'method' : 'POST',
        'params' : [
            param.path('senderAddress', 'The short code of your app w/o the starting 2158 number.', 'int'),
            param.query('access_token', 'Subscribers access token', 'string', true),
            param.form('message', 'The message', 'string'),
            param.form('address', 'The subscriber number who owns the access token', 'string'),
            param.form('clientCorrelator', 'Unique identification of string to avoid multiple sending', 'string')
        ],
        'errorResponses' : [
            {
                'code' : '200',
                'reason' : 'Success'
            },
            {
                'code' : '201',
                'reason' : 'Created. The message resource was created and is being queued for delivery (forwarded to SMSC)'
            },
            {
                'code' : '204',
                'reason' : 'No content'
            },
            {
                'code' : '400',
                'reason' : 'Bad request; check the error message and correct the request syntax'
            },
            {
                'code' : '401',
                'reason' : 'Authentication failure'
            },
            {
                'code' : '403',
                'reason' : 'Forbidden; the requested resource state is not supported'
            },
            {
                'code' : '404',
                'reason' : 'Not found: mistake in the host or path of the service URI, or the resource is not implemented'
            },
            {
                'code' : '405',
                'reason' : 'Method not supported: e.g. only GET and not POST is supported for a given resource'
            },
            {
                'code' : '503',
                'reason' : 'Server busy and service unavailable. Please retry the request'
            }
        ],
        'nickname' : 'sendSMS'
    },
    'action' : function(req, res) {
        var urlParse = url.parse(req.url, true);
        var post = JSON.stringify(req.body);
        var header = header = {
            'Content-Type' : 'application/json'
        };

        httpRequest.post(urlParse.pathname, urlParse.query, post, header, function(request, response, data) {
            res.writeHead(response.statusCode);
            res.end(JSON.stringify(data));
        });
    }
};

exports.retrieveSMS = {
    'spec' : {
        'description' : 'Send and receive SMS messages',
        'path' : '/smsmessaging/v1/inbound/registrations/{senderAddress}/messages',
        'notes' : 'Retrieve SMS sent to your Web application, which is identified by registrationId (App Short Code Suffix)',
        'summary' : 'Retrieve SMS',
        'method' : 'GET',
        'params' : [
            param.path('senderAddress', 'The short code of your app w/o the starting 2158 number.', 'int'),
            param.query('access_token', 'Subscribers access token', 'string', true),
            param.query('maxBatchSize', 'Query batch size', 'int')
        ],
        'errorResponses' : [
            {
                'code' : '200',
                'reason' : 'Success'
            },
            {
                'code' : '201',
                'reason' : 'Created. The message resource was created and is being queued for delivery (forwarded to SMSC)'
            },
            {
                'code' : '204',
                'reason' : 'No content'
            },
            {
                'code' : '400',
                'reason' : 'Bad request; check the error message and correct the request syntax'
            },
            {
                'code' : '401',
                'reason' : 'Authentication failure'
            },
            {
                'code' : '403',
                'reason' : 'Forbidden; the requested resource state is not supported'
            },
            {
                'code' : '404',
                'reason' : 'Not found: mistake in the host or path of the service URI, or the resource is not implemented'
            },
            {
                'code' : '405',
                'reason' : 'Method not supported: e.g. only GET and not POST is supported for a given resource'
            },
            {
                'code' : '503',
                'reason' : 'Server busy and service unavailable. Please retry the request'
            }
        ],
        'nickname' : 'retrieveSMS'
    },
    'action' : function(req, res) {
        var urlParse = url.parse(req.url, true);
        var post = JSON.stringify(req.body);
        var header = header = {
            'Content-Type' : 'application/json'
        };

        httpRequest.get(urlParse.pathname, urlParse.query, post, header, function(request, response, data) {
            res.writeHead(response.statusCode);
            res.end(JSON.stringify(data));
        });
    }
};

exports.chargeClient = {
    'spec' : {
        'description' : 'Charge subscribers',
        'path' : '/payment/v1/transactions/amount',
        'notes' : 'Charges the subscriber with the specified amount',
        'summary' : 'Charge Subscriber',
        'method' : 'POST',
        'params' : [
            param.query('access_token', 'Subscribers access token', 'string', true),
            param.form('endUserId', 'Subscriber\'s number', 'string'),
            param.form('referenceCode', 'Unique transaction ID with a format of [SHORT_CODE_WITHOUT_2158]+####### where ####### is an incremented number beginning from 1000001', 'string'),
            param.form('amount', 'The amount', 'string', '1.00'),
            param.form('clientCorrelator', 'Unique identification of string to avoid multiple charging to user', 'string')
        ],
        'errorResponses' : [
            {
                'code' : '201',
                'reason' : 'Created. The charge was succesful'
            },
            {
                'code' : '400',
                'reason' : 'Bad request; check the error message and correct the request syntax'
            },
            {
                'code' : '401',
                'reason' : 'Authentication failure'
            },
            {
                'code' : '403',
                'reason' : 'Forbidden; please provide authentication credentials'
            },
            {
                'code' : '404',
                'reason' : 'Not found: mistake in the host or path of the service URI, or the resource is not implemented'
            },
            {
                'code' : '405',
                'reason' : 'Method not supported: e.g. only GET and not POST is supported for a given resource'
            },
            {
                'code' : '503',
                'reason' : 'Server busy and service unavailable. Please retry the request'
            }
        ],
        'nickname' : 'chargeClient'
    },
    'action' : function(req, res) {
        var urlParse = url.parse(req.url, true);
        req.body['transactionOperationStatus'] = 'charged';
        var post = JSON.stringify(req.body);
        var header = header = {
            'Content-Type' : 'application/json'
        };

        httpRequest.post(urlParse.pathname, urlParse.query, post, header, function(request, response, data) {
            res.writeHead(response.statusCode);
            res.end(JSON.stringify(data));
        });
    }
};