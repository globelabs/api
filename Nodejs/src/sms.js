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
    var SEND_STATUS_URL = '/smsmessaging/%s/outbound/%s/requests/%s/deliveryInfos';
    var SEND_SUBSCRIBE_URL = '/smsmessaging/%s/outbound/%s/subscriptions';
    // Receive SMS
    var RECEIVE_URL = '/smsmessaging/%s/inbound/registrations/%s/messages';
    var RECEIVE_SUBSCRIBE_URL = '/smsmessaging/%s/inbound/subscriptions';

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
     * @deprecated Not yet implemented
     * Queries the delivery status of an SMS.
     * 
     * @param {string}   requestId
     * @param {function} callback
     * @return {SMS}
     */
    p.getDeliveryStatus = function(requestId, callback) {
        // prepend the protocol identifier

        // build the url
        var url = util.format(SEND_STATUS_URL, this.version, encodeURIComponent(_parseShortCode.call(this)), requestId);

        var query = {
            'access_token' : this.clientToken
        };

        // sends a request get
        $.get(url, query, callback);

        return this;
    };

    /**
     * @deprecated Not yet implemented
     * Subscribes to SMS delivery notifications.
     * 
     * @param {string}   [clientCorrelator]
     * @param {string}   [criteria]
     * @param {string}   [callbackData]
     * @param {function} callback
     * @return {SMS}
     */
    p.subscribeDeliveryNotification = function(clientCorrelator, criteria, callbackData, callback) {
        // build the query
        var post = {
            notifyURL : notifyURL
        };

        // finds the callback function
        if (typeof clientCorrelator === 'function') {
            callback = clientCorrelator;
            clientCorrelator = undefined;
        } else if (typeof criteria === 'function') {
            callback = criteria;
            criteria = undefined;
        } else if (typeof callbackData === 'function') {
            callback = callbackData;
            callbackData = undefined;
        }

        // optional parameters checking
        if (clientCorrelator) {
            post['clientCorrelator'] = clientCorrelator;
        }

        if (criteria) {
            post['criteria'] = criteria;
        }

        if (callbackData) {
            post['callbackData'] = callbackData;
        }

        // build the url
        var url = util.format(SEND_SUBSCRIBE_URL, this.version, encodeURIComponent(_parseShortCode.call(this)));

        var query = {
            'access_token' : this.clientToken
        };

        // sends a request post
        $.post(url, query, JSON.stringify(post), headers, callback);

        return this;
    };

    /**
     * @deprecated Not yet implemented
     * Unsubscribes to SMS delivery notifications.
     * 
     * @param {string}   subscriptionID
     * @param {function} callback
     * @return {SMS}
     */
    p.unsubscribeDeliveryNotification = function(subscriptionID, callback) {
        // build the url
        var url = util.format(SEND_SUBSCRIBE_URL, this.version, encodeURIComponent(_parseShortCode.call(this)));

        var post = {
            subscriptionID : subscriptionID
        };

        var query = {
            'access_token' : this.clientToken
        };

        // sends a request delete
        $.delete(url, query, JSON.stringify(post), headers, callback);

        return this;
    };

    /**
     * @deprecated Not yet implemented
     * Retrieves messages sent on your application.
     * 
     * @param {string}        registrationID
     * @param {string|number} [maxBatchSize]
     * @param {function}      callback
     * @return {SMS}
     */
    p.retrieveMessages = function(registrationID, maxBatchSize, callback) {
        var post = {
            registrationID : registrationID
        };

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
        $.get(url, query, JSON.stringify(post), headers, callback);

        return this;
    };

    /**
     * @deprecated Not yet implemented
     * Subscribes notifications of messages sent to your application.
     * 
     * @param {string}   [criteria]
     * @param {string}   [notificationFormat]
     * @param {string}   [clientCorrelator]
     * @param {string}   [callbackData]
     * @param {function} callback
     * @return {SMS}
     */
    p.subscribeNewMessageNotification = function(
            criteria,
            notificationFormat,
            clientCorrelator,
            callbackData,
            callback) {
        // finds the callback function
        if (typeof criteria === 'function') {
            callback = criteria;
            criteria = undefined;
        } else if (typeof notificationFormat === 'function') {
            callback = notificationFormat;
            notificationFormat = undefined;
        } else if (typeof clientCorrelator === 'function') {
            callback = clientCorrelator;
            clientCorrelator = undefined;
        } else if (typeof callbackData === 'function') {
            callback = callbackData;
            callbackData = undefined;
        }

        // build the query
        var post = {
            destinationAddress : this.shortCode
        };

        // optional parameters checking
        if (criteria) {
            post['criteria'] = criteria;
        }

        if (notificationFormat) {
            post['notificationFormat'] = notificationFormat;
        }

        if (clientCorrelator) {
            post['clientCorrelator'] = clientCorrelator;
        }

        if (callbackData) {
            post['callbackData'] = callbackData;
        }

        // build the url
        var url = util.format(RECEIVE_SUBSCRIBE_URL, this.version);

        var query = {
            'access_token' : this.clientToken
        };

        // sends a request post
        $.post(url, query, JSON.stringify(post), headers, callback);

        return this;
    };

    /**
     * @deprecated Not yet implemented
     * Unsubscribes notifications of messages sent to your application.
     * 
     * @param {string}   subscriptionID
     * @param {function} callback
     * @return {SMS}
     */
    p.unsubscribeNewMessageNotification = function(subscriptionID, callback) {
        // build the url
        var url = util.format(RECEIVE_SUBSCRIBE_URL, this.version);

        var query = {
            'access_token' : this.clientToken
        };

        var post = {
            subscriptionID : subscriptionID
        };

        // sends a request delete
        $.delete(url, query, JSON.stringify(post), headers, callback);

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