var express = require('express'),
        swagger = require('swagger-node-express');

var resources = require('./resources.js');

var app = express();
app.use(express.bodyParser());

// Set the main handler in swagger to the express app
swagger.setAppHandler(app);

// Add methods to swagger
var models = require('./models.js'); // loads up models
swagger.addModels(models).
        addPost(resources.sendSMS).
        addPost(resources.chargeClient);

// Routes
swagger.configureSwaggerPaths('', '/resources', '');

// Configures the app's base path and api version.
swagger.configure('http://localhost', '0.0');

app.all('*', function(req, res, next) {
    // use '*' here to accept any origin
    res.set('Access-Control-Allow-Origin', '*');
    res.set('Access-Control-Allow-Methods', 'GET, POST');
    res.header('Access-Control-Allow-Headers',
            'Access-Control-Allow-Headers', 'Origin, X-Requested-With, Content-Type, Accept');
    next();
});

// Serve up swagger ui at /docs via static route
var docs_handler = express.static(__dirname + '/swagger-ui-master/');

app.get('/', function(req, res, next) {
    res.writeHead(302, { 'Location' : '/swagger/' });
    res.end();
    return;
});

app.get(/^\/swagger(\/.*)?$/, function(req, res, next) {
    if (req.url === '/swagger') { // express static barfs on root url w/o trailing slash
        res.writeHead(302, { 'Location' : req.url + '/' });
        res.end();
        return;
    }
    // take off leading /docs so that connect locates file correctly
    req.url = req.url.substr('/swagger'.length);
    return docs_handler(req, res, next);
});

// Start the server on port 80
app.listen(80);