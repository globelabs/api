class Payment
    # CONSTANTS
    URL = 'http://devapi.globelabs.com.ph/payment/%s/transactions/amount'
	
    # CLASS VARIABLES
    @@accessToken  = String.new
    @@version      = 'v1'
    @@params       = Array.new
	
    # Initialize Access Token
    def initialize(accessToken)
        @@accessToken = accessToken
    end
	
    # Set the parameters need for charging a Subscriber
    def charge(chargeTo, amount, referenceCode)
        @@params = {
            'transactionOperationStatus' => 'charged',
            'access_token'               => @@accessToken,
            'endUserId'                  => chargeTo,
            'amount'                     => amount,
            'referenceCode'              => referenceCode
        }
		
        return self
    end
	
    # Sends a request using POST method
    def send
        url = sprintf(URL, @@version)
		
        #Request as POST METHOD
        uri      = URI.parse(url)
        http     = Net::HTTP.new(uri.host, uri.port)
        request  = Net::HTTP::Post.new(url)
		
        request.set_form_data(@@params)
		
        response = http.request(request)
		
        return response.body
    end
	
	# Set the API Version
    def setVersion(version)
        @@version = version
        return self
    end
end
