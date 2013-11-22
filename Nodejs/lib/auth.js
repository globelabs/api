/**
 * OAuth Class.
 * 
 * @vendor  Globe Labs
 * @package lib
 * @author  Ian Mark Muninio <ianmuninio@openovate.com>
 * @since   1.0.0
 */
module.exports = function() {
    var $ = this, c = function(appId, appSecret, redirectUrl, requestUrl, accessUrl) {
        this.__construct.call(this, appId, appSecret, redirectUrl, requestUrl, accessUrl);
    }, p = c.prototype;

    /* Static Properties
    -------------------------------*/
    /* Private Properties
    -------------------------------*/
    /* Construct
    -------------------------------*/
    /**
     * Load defaults.
     * 
     * @param {string} appId
     * @param {string} appSecret
     * @param {string} redirectUrl
     * @param {string} requestUrl
     * @param {string} accessUrl
     */
    p.__construct = function(appId, appSecret, redirectUrl, requestUrl, accessUrl) {
        this.appId = appId;
        this.appSecret = appSecret;
        this.redirectUrl = redirectUrl;
        this.requestUrl = requestUrl;
        this.accessUrl = accessUrl;

        this.request = require('./request')()(accessUrl);
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
        this.request.setLogger(logger);

        return this;
    };

    /**
     * Gets the logger.
     * 
     * @return {logger}
     */
    p.getLogger = function() {
        return this.request.getLogger();
    };

    /**
     * Gets the login url.
     * 
     * @return {this}
     */
    p.getLoginUrl = function() {
        var query = {
            app_id : this.appId,
            app_secret : this.appSecret,
            redirect_url : this.redirectUrl
        };

        return this.requestUrl + '?' + this.request.httpBuildQuery(query);
    };

    /**
     * Gets the access token.
     * 
     * @param {string}   code
     * @param {function} callback
     * @return {this}
     */
    p.getAccessToken = function(code, callback) {
        var query = {
            app_id : this.appId,
            app_secret : this.appSecret,
            redirect_url : this.redirectUrl,
            code : code
        };

        // sends request
        this.request.post(null, query, callback);

        return this;
    };

    /* Private Methods
    -------------------------------*/
    /* Adaptor
    -------------------------------*/
    var module = function(appId, appSecret, redirectUrl, requestUrl, accessUrl) {
        return new c(appId, appSecret, redirectUrl, requestUrl, accessUrl);
    };

    module.prototype = p;

    return module;
};