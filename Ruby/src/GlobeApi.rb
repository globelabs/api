require 'json'
require 'net/https'
require_relative 'Sms'
require_relative 'Auth'
require_relative 'Payment'

class GlobeApi
    #CLASS VARIABLES
    @@accessToken  	= String.new
    @@shortCode    	= String.new
	
	def auth(appId, appSecret)
		return Auth.new(appId, appSecret)
	end
	
    # Returns SMS
    def sms(accessToken, shortCode)
        return Sms.new(accessToken, shortCode)
    end
	
	# Returns Payment
    def payment(accessToken)
        return Payment.new(accessToken)
    end
end