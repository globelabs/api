/**
 * Globe Class Factory.
 * 
 * @vendor  Globe Labs
 * @package lib
 * @author  Ian Mark Muninio <ianmuninio@openovate.com>
 * @since   1.0.0
 */
module.exports = function() {
    var c = function(version) {
        this.__construct.call(this, version);
    }, p = c.prototype;

    /* Static Properties
    -------------------------------*/
    var URL = 'http://devapi.globelabs.com.ph';
    var REQUEST_URL = 'http://developer.globelabs.com.ph/dialog/oauth';
    var ACCESS_URL = 'http://developer.globelabs.com.ph/oauth/access_token';

    /* Private Properties
    -------------------------------*/
    var _cache = { };

    /* Construct
    -------------------------------*/
    /**
     * Load defaults.
     * 
     * @param {string} version
     */
    p.__construct = function(version) {
        this.version = version || 'v1';
        request = load.call(this, 'request', URL);
    };

    /* Public Methods
    -------------------------------*/
    /**
     * Gest the class of Auth.
     * 
     * @param {string} appId
     * @param {string} appSecret
     * @returns {globe.Auth}
     */
    p.Auth = function(appId, appSecret) {
        return load.call(this, 'auth', appId, appSecret, null, REQUEST_URL, ACCESS_URL);
    };

    /**
     * Gets the class of SMS.
     * 
     * @param {string} shortCode
     * @param {string} clientAddress
     * @param {string} clientToken
     * @returns {globe.SMS}
     */
    p.SMS = function(shortCode, clientAddress, clientToken) {
        var request = load.call(this, 'request', URL);
        return load.call(request, 'sms', this.version, shortCode, clientAddress, clientToken);
    };

    /**
     * Gets the class of Payment.
     * 
     * @param {string} clientAddress
     * @param {string} clientToken
     * @returns {globe.Payment}
     */
    p.Payment = function(clientAddress, clientToken) {
        var request = load.call(this, 'request', URL);
        return load.call(request, 'payment', this.version, clientAddress, clientToken);
    };

    /* Private Methods
    -------------------------------*/
    /**
     * Loads the Globe API classes.
     * 
     * @param {*..}
     * @returns {Globe}
     */
    var load = function() {
        var args = Array.prototype.slice.apply(arguments);

        var key = args.shift();
        if (!_cache[key]) {
            _cache[key] = require('./' + key + '.js').call(this);
        }

        return _cache[key].apply(this, args);
    };

    /* Adaptor
    -------------------------------*/
    var module = function(version) {
        return new c(version);
    };

    module.prototype = p;

    return module;
}();