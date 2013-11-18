class Charge
    # CONSTANTS
    URL = 'http://devapi.globelabs.com.ph/payment/v1/transactions/amount'
	
    # CLASS VARIABLES
    @@accessToken    = String.new
    @@shortCode      = String.new
	
    # Initialize Access Token and Short Code
    def initialize(accessToken, shortCode)
        @@accessToken  = accessToken
        @@shortCode    = shortCode
    end
	
    # Sends a Charge request to Subscriber
    #
    # * Sets the string query for the parameters
	# * Sends data using POST METHOD
    def send(chargeTo, amount, referenceCode)
        #Request as POST METHOD
        uri        = URI.parse(URL)
        http       = Net::HTTP.new(uri.host, uri.port)
        request    = Net::HTTP::Post.new(URL)
		
        params  = {
            'transactionOperationStatus'  => 'charged',
            'access_token'                => @@accessToken,
            'endUserId'                   => chargeTo,
            'amount'                      => amount,
            'referenceCode'               => referenceCode
        }
		
        request.set_form_data(params)
		
        response 	= http.request(request)
		
        return response.body
    end
end