/**
 * This class allows to send request to the Globe API.
 * 
 * @vendor  Globe Labs
 * @package lib
 * @author  Ian Mark Muninio <ianmuninio@openovate.com>
 * @since   1.0.0
 */
module.exports = function() {
    var $ = this, c = function(host, port) {
        this.__construct.call(this, host, port);
    }, p = c.prototype;

    /* Static Properties
    -------------------------------*/
    /* Private Properties
    -------------------------------*/
    var http = require('http');
    var querystring = require('querystring');

    /* Construct
    -------------------------------*/
    /**
     * Load defaults.
     * 
     * @param {string} host
     * @param {number} [port=80]
     */
    p.__construct = function(host, port) {
        var split = (host || '').replace(/^(http:\/\/|https:\/\/)/g, '').
                split('/');

        this.host = split.shift();
        this.port = port || 80;
        this.path = split.join('/');

        if (this.path) {
            this.path = '/' + this.path;
        }

        // sets the default logger
        this.logger = {
            info : function() {
            },
            log : function() {
            },
            error : function() {
            }
        };
    };

    /* Public Methods
    -------------------------------*/
    /**
     * Sets the logger.
     * 
     * @param {logger} logger
     * @return {request}
     */
    p.setLogger = function(logger) {
        this.logger = logger || this.logger;

        return this;
    };

    /**
     * Gets the logger.
     * 
     * @return {logger}
     */
    p.getLogger = function() {
        return this.logger;
    };

    /**
     * Sends a request GET to the host.
     * 
     * @param {string}   [path]
     * @param {object}   [query]
     * @param {object}   [post]
     * @param {object}   [headers]
     * @param {function} callback
     * @return {request}
     */
    p.get = function(path, query, post, headers, callback) {
        // get the response of the request
        _getResponse.call(this, 'GET', path, query, post, headers, callback);

        return this;
    };

    /**
     * Sends a request POST to the host.
     * 
     * @param {string}   [path]
     * @param {object}   [query]
     * @param {object}   [post]
     * @param {object}   [headers]
     * @param {function} callback
     * @return {request}
     */
    p.post = function(path, query, post, headers, callback) {
        // get the response of the request
        _getResponse.call(this, 'POST', path, query, post, headers, callback);

        return this;
    };

    /**
     * Sends a request PUT to the host.
     * 
     * @param {string}   [path]
     * @param {object}   [query]
     * @param {object}   [post]
     * @param {object}   [headers]
     * @param {function} callback
     * @return {request}
     */
    p.put = function(path, query, post, headers, callback) {
        // get the response of the request
        _getResponse.call(this, 'PUT', path, query, post, headers, callback);

        return this;
    };

    /**
     * Sends a request DELETE to the host.
     * 
     * @param {string}   [path]
     * @param {object}   [query]
     * @param {object}   [post]
     * @param {object}   [headers]
     * @param {function} callback
     * @return {request}
     */
    p.delete = function(path, query, post, headers, callback) {
        // get the response of the request
        _getResponse.call(this, 'DELETE', path, query, post, headers, callback);

        return this;
    };

    /**
     * HTTP Build Query. Parses the object as HTTP URI Encoded String.
     * Same as the http_build_query of PHP.
     * 
     * @param {object} formdata
     * @param {number} [numeric_prefix]
     * @param {string} [arg_separator='&']
     * @return {string} built query
     */
    p.httpBuildQuery = function(formdata, numeric_prefix, arg_separator) {
        var value, key, tmp = []; // helper variables

        // helper function
        var httpBuildQueryHelper = function(key, val, arg_separator) {
            var k, tmp = []; // helper variables

            // convert val boolean type to 1 for true and 0 for false
            if (val === true) {
                val = '1';
            } else if (val === false) {
                val = '0';
            }

            // if val is not null
            if (val !== null && val !== undefined) {
                // if value of val is object
                if (typeof val === 'object') {
                    // loop on val
                    for (k in val) {
                        // checks each val properties if not null
                        if (val[k] !== null) {
                            // recurse the val property and push to tmp variable
                            tmp.push(httpBuildQueryHelper(key + '[' + k + ']', val[k], arg_separator));
                        }
                    }

                    // join and return tmp
                    return tmp.join(arg_separator);
                } else if (typeof val !== 'function') { // checks if val is not a function
                    // return key and val as URI encoded
                    return encodeURIComponent(key) + '=' + encodeURIComponent(val);
                } else {
                    // function query is not valid
                    throw new Error('There was an error processing for httpBuildQuery().');
                }
            } else {
                // just return empty string
                return '';
            }
        };

        // is arg_seprator is not set
        if (!arg_separator) {
            arg_separator = '&'; // set the default value
        }

        // exception-handler block
        try {
            // loop on each formdata object
            for (key in formdata) {
                value = formdata[key];

                // if numeric_prefix is defined and a number
                if (numeric_prefix && !isNaN(key)) {
                    key = String(numeric_prefix) + key; // prepend the numeric_prefix
                }

                // call the helper function
                var query = httpBuildQueryHelper(key, value, arg_separator);

                // if query is not empty
                if (query !== '') {
                    tmp.push(query); // push the query
                }
            }
        } catch (err) {
            console.log(err); // prints out the error
        }

        // return the query
        return tmp.join(arg_separator);
    };

    /* Private Methods
    -------------------------------*/
    /**
     * Gets the response.
     * 
     * @param {string}   method
     * @param {string}   [path]
     * @param {object}   [query]
     * @param {object}   [post]
     * @param {object}   headers
     * @param {function} callback
     * @return {request}
     */
    var _getResponse = function(method, path, query, post, headers, callback) {
        // finds the callback function
        if (typeof path === 'function') {
            callback = path;
            path = '/';
            query = null;
            post = null;
            headers = { };
        } else if (typeof query === 'function') {
            callback = query;
            query = null;
            post = null;
            headers = { };
        } else if (typeof post === 'function') {
            callback = post;
            post = null;
            headers = { };
        } else if (typeof headers === 'function') {
            callback = headers;
            headers = { };
        }

        if (!headers || typeof headers !== 'object') {
            headers = { };
        }

        // instance
        var self = this;

        // build the options
        var options = {
            host : self.host,
            port : self.port,
            path : self.path + (path || ''),
            method : method || 'GET',
            headers : headers
        };

        // checks if method is POST, PUT or DELETE
        if (method !== 'GET') {
            if (typeof post !== 'string') {
                // checks if query is undefined or null
                if (post === undefined || post === null) {
                    post = ''; // set to empty string
                }

                // build the query
                post = self.httpBuildQuery(post);
            }

            // sets the content-length
            headers['Content-Length'] = post.length; // sets the content length
        }

        if (typeof query !== 'string') {
            query = self.httpBuildQuery(query);
        }

        if (options['path'].lastIndexOf('?') !== --options['path'].length) {
            query = '?' + query;
        }

        // append query
        options['path'] += query;

        // logged request
        _loggedRequest.call(self, options, query, post);

        // send the request
        var request = http.request(options, function(response) {
            self.logger.info("Received response..");

            response.setEncoding('utf8'); // default encoding

            var buffer = null;

            // event on the data receives
            response.on('data', function(chunk) {
                // if buffer is null
                if (buffer === null) {
                    buffer = ''; // set the default value
                }

                buffer += chunk; // append the chunk
            });

            // event on end of response
            response.on('end', function() {
                // check if is has content-type
                if (response.headers['content-type']) {
                    // if content-type is using json
                    if (response.headers['content-type'].indexOf('application/json') === 0) {
                        try {
                            buffer = JSON.parse(buffer); // parse buffer to a JSON Object
                        } catch (err) {
                            response.error = err.stack;
                        }
                    } else if (response.headers['content-type'].
                            indexOf('application/x-www-form-urlencoded') === 0) { // if content-type is using form
                        buffer = querystring.parse(buffer);
                    }
                }

                // set response.body
                response.body = buffer;

                // fire the request
                callback(request, response);
            });
        });

        // event handler on request error
        request.on('error', function(error) {
            callback(request, null, error); // fire the error
        });

        // checks if method is POST, PUT or DELETE
        if (method !== 'GET' && post) {
            request.write(post); // sends the query
        }

        request.end(); // end the request

        return this;
    };

    /**
     * Logged the http request.
     * 
     * @param {object} options
     * @param {string} query
     * @param {string} data
     */
    var _loggedRequest = function(options, query, data) {
        this.logger.info('HTTP Options:', options);
        this.logger.info('Query:', query);
        this.logger.info('Body:', data);
    };

    /* Adaptor
    -------------------------------*/
    var module = function(host, port) {
        return new c(host, port);
    };

    module.prototype = p;

    return module;
};