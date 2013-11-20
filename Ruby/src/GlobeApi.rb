# Require Default Ruby Libraries
require 'json'
require 'net/https'

# Require Classes for Globe API
require_relative 'Sms'
require_relative 'Auth'
require_relative 'Payment'

class GlobeApi
    # CLASS VARIABLES
    @@accessToken   = String.new
    @@shortCode     = String.new
	
    # Initialize Authentication
    def auth(appId, appSecret)
        return Auth.new(appId, appSecret)
    end
	
    # Returns Payment
    def payment(accessToken)
        return Payment.new(accessToken)
    end
	
    # Returns SMS
    def sms(accessToken, shortCode)
        return Sms.new(accessToken, shortCode)
    end
end
