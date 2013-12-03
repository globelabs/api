/**
 * The Globe Payment API wrapper class allows you to charge and refund payments easily.
 * 
 * @vendor  Globe Labs
 * @package lib
 * @author  Ian Mark Muninio <ianmuninio@openovate.com>
 * @since   1.0
 */
module.exports = function() {
    var $ = this, c = function(version, clientAddress, clientToken) {
        this.__construct.call(this, version, clientAddress, clientToken);
    }, p = c.prototype;

    /* Static Properties
    -------------------------------*/
    // Payment
    var TRANSACTION_URL = '/payment/%s/transactions/amount';

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
     * @param {string} clientAddress
     * @param {string} clientToken
     */
    p.__construct = function(version, clientAddress, clientToken) {
        this.version = version;
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
     * Charge an amount to the end user’s bill.
     * 
     * @param {string}   amount
     * @param {string}   referenceCode
     * @param {string}   [description]
     * @param {string}   [clientCorrelator]
     * @param {string}   [onBehalfOf]
     * @param {string}   [purchaseCategoryCode]
     * @param {string}   [channel]
     * @param {number}   [taxAmount]
     * @param {string}   [serviceID]
     * @param {string}   [productID]
     * @param {function} callback
     * @return {Payment}
     */
    p.charge = function(amount, referenceCode, description, clientCorrelator,
            onBehalfOf, purchaseCategoryCode, channel, taxAmount, serviceID,
            productID, callback) {
        // finds the callback function
        if (typeof description === 'function') {
            callback = description;
            description = undefined;
            clientCorrelator = undefined;
            onBehalfOf = undefined;
            purchaseCategoryCode = undefined;
            channel = undefined;
            taxAmount = undefined;
            serviceID = undefined;
            productID = undefined;
        } else if (typeof clientCorrelator === 'function') {
            callback = clientCorrelator;
            clientCorrelator = undefined;
            onBehalfOf = undefined;
            purchaseCategoryCode = undefined;
            channel = undefined;
            taxAmount = undefined;
            serviceID = undefined;
            productID = undefined;
        } else if (typeof onBehalfOf === 'function') {
            callback = onBehalfOf;
            onBehalfOf = undefined;
            purchaseCategoryCode = undefined;
            channel = undefined;
            taxAmount = undefined;
            serviceID = undefined;
            productID = undefined;
        } else if (typeof purchaseCategoryCode === 'function') {
            callback = purchaseCategoryCode;
            purchaseCategoryCode = undefined;
            channel = undefined;
            taxAmount = undefined;
            serviceID = undefined;
            productID = undefined;
        } else if (typeof channel === 'function') {
            callback = channel;
            channel = undefined;
            taxAmount = undefined;
            serviceID = undefined;
            productID = undefined;
        } else if (typeof taxAmount === 'function') {
            callback = taxAmount;
            taxAmount = undefined;
            serviceID = undefined;
            productID = undefined;
        } else if (typeof serviceID === 'function') {
            callback = serviceID;
            serviceID = undefined;
            productID = undefined;
        } else if (typeof productID === 'function') {
            callback = productID;
            productID = undefined;
        }
        
        if (amount === null || Math.abs(amount) === NaN) {
            throw new Error('Amount needs to be a number');
            return;
        } else if (!referenceCode) {
            throw new Error('Reference code is expected on charging');
            return;
        } else if (!callback || typeof callback !== 'function') {
            throw new Error('Callback must be a function');
            return;
        }

        // build the query
        var post = {
            endUserId : this.clientAddress,
            referenceCode : referenceCode,
            transactionOperationStatus : 'charged',
            amount : amount
        };

        // optional parameters checking
        if (description) {
            post['description'] = description;
        }

        if (clientCorrelator) {
            post['clientCorrelator'] = clientCorrelator;
        }

        if (onBehalfOf) {
            post['onBehalfOf'] = onBehalfOf;
        }

        if (purchaseCategoryCode) {
            post['purchaseCategoryCode'] = purchaseCategoryCode;
        }

        if (channel) {
            post['channel'] = channel;
        }

        if (taxAmount) {
            post['taxAmount'] = taxAmount;
        }

        if (serviceID) {
            post['serviceID'] = serviceID;
        }

        if (productID) {
            post['productID'] = productID;
        }

        // build the url
        var url = util.format(TRANSACTION_URL, this.version);

        var query = {
            'access_token' : this.clientToken
        };

        // sends a request post
        $.post(url, query, JSON.stringify(post), headers, callback);

        return this;
    };

    /* Private Methods
    -------------------------------*/
    /* Adaptor
    -------------------------------*/
    var module = function(version, clientAddress, clientToken) {
        return new c(version, clientAddress, clientToken);
    };

    module.prototype = p;

    return module;
};