require_relative 'Outbound'
require_relative 'Charge'
require_relative 'Refund'

class GlobeApi
    #CLASS VARIABLES
    @@accessToken  = String.new
    @@shortCode    = String.new
	
    # Initialize Access Token and Short Code
    def initialize(accessToken, shortCode)
        @@accessToken   = accessToken
        @@shortCode     = shortCode
    end
	
    # Returns Outbound SMS
    def createSms(subscriber, message)
        object = Outbound.new(@@accessToken, @@shortCode)
        return object.send(subscriber, message)
    end
	
	# Returns Charge
    def createCharge(chargeTo, amount, referenceCode)
        object = Charge.new(@@accessToken, @@shortCode)
        return object.send(chargeTo, amount, referenceCode)
    end
	
	# Returns Refund
    def createRefund
        object = Refund.new(@@accessToken, @@shortCode)
        return object.send
    end
end