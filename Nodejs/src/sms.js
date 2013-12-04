/**
 * The Globe SMS API wrapper class allows you to send, retrieve, subscribe and unsubscribe messages easily.
 * 
 * @vendor  Globe Labs
 * @package lib
 * @author  Ian Mark Muninio <ianmuninio@openovate.com>
 * @since   1.0
 */
module.exports = function() {
    var $ = this, c = function(version, shortCode, clientAddress, clientToken) {
        this.__construct.call(this, version, shortCode, clientAddress, clientToken);
    }, p = c.prototype;

    /* Static Properties
    -------------------------------*/
    // Send SMS
    var SEND_URL = '/smsmessaging/%s/outbound/%s/requests';
    // Receive SMS
    var RECEIVE_URL = '/smsmessaging/%s/inbound/registrations/%s/messages';

    /* Private Properties
    -------------------------------*/
    var util = require('util');
    var headers = {
        'Content-Type' : 'application/json'
    };

    /* Construct
    -------------------------------*/
    /**
     * Load defaults.
     * 
     * @param {string} version
     * @param {string} shortCode
     * @param {string} clientAddress
     * @param {string} clientToken
     */
    p.__construct = function(version, shortCode, clientAddress, clientToken) {
        this.version = version;
        this.shortCode = shortCode;
        this.clientAddress = clientAddress;
        this.clientToken = clientToken;
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
        $.setLogger(logger);

        return this;
    };

    /**
     * Gets the logger.
     * 
     * @return {logger}
     */
    p.getLogger = function() {
        return $.getLogger();
    };

    /**
     * Sends a message.
     * 
     * @param {string}   message
     * @param {string}   [clientCorrelator]
     * @param {function} callback
     * @return {SMS}
     */
    p.sendMessage = function(message, clientCorrelator, callback) {
        // finds the callback function
        if (typeof clientCorrelator === 'function') {
            callback = clientCorrelator;
            clientCorrelator = undefined;
        }
        
        if (!message || typeof message !== 'string') {
            throw new Error('Message needs to be a string');
            return;
        } else if (!callback || typeof callback !== 'function') {
            throw new Error('Callback must be a function');
            return;
        }

        // build the query
        var post = {
            address : this.clientAddress,
            message : message,
            senderAddress : this.shortCode
        };

        if (clientCorrelator) {
            post['clientCorrelator'] = clientCorrelator;
        }

        // build the url
        var url = util.format(SEND_URL, this.version, encodeURIComponent(_parseShortCode.call(this)));

        var query = {
            'access_token' : this.clientToken
        };

        // sends a request post
        $.post(url, query, JSON.stringify(post), headers, callback);

        return this;
    };

    /**
     * Retrieves messages sent on your application.
     * 
     * @param {string|number} [maxBatchSize]
     * @param {function}      callback
     * @return {SMS}
     */
    p.retrieveMessages = function(maxBatchSize, callback) {
        // empty query
        var query = {
            'access_token' : this.clientToken
        };

        // finds the callback function
        if (typeof maxBatchSize === 'function') {
            callback = maxBatchSize;
            maxBatchSize = undefined;
        }

        // if maxBatchSize parameter is set
        if (maxBatchSize) {
            query['maxBatchSize'] = maxBatchSize; // set the maxBatchSize
        }

        // build the url
        var url = util.format(RECEIVE_URL, this.version, encodeURIComponent(_parseShortCode.call(this)));

        // sends a request get
        $.get(url, query, null, headers, callback);

        return this;
    };

    /* Private Methods
    -------------------------------*/
    var _parseShortCode = function() {
        return this.shortCode.toString().replace(/^2158/g, '');
    };

    /* Adaptor
    -------------------------------*/
    var module = function(version, shortCode, clientAddress, clientToken) {
        return new c(version, shortCode, clientAddress, clientToken);
    };

    module.prototype = p;

    return module;
};