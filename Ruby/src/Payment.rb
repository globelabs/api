class Payment
    # CONSTANTS
    URL = 'http://devapi.globelabs.com.ph/payment/%s/transactions/amount'
	
    # CLASS VARIABLES
    @@accessToken  = String.new
    @@subscriber   = String.new
    @@version      = 'v1'
	
    # Initialize Access Token
    def initialize(accessToken, subscriber)
        @@accessToken = accessToken
	    @@subscriber  = subscriber
    end
	
    # Sends a request using POST method
    def charge(amount, referenceCode)
        url = sprintf(URL, @@version)
		
        #Request as POST METHOD
        uri      = URI.parse(url)
        http     = Net::HTTP.new(uri.host, uri.port)
        request  = Net::HTTP::Post.new(url)
		
        params = {
            'transactionOperationStatus' => 'charged',
            'access_token'               => @@accessToken,
            'endUserId'                  => @@subscriber,
            'amount'                     => amount,
            'referenceCode'              => referenceCode
        }
		
        request.set_form_data(params)
		
        response = http.request(request)
		
        return response.body
    end
	
    # Set the API Version
    def setVersion(version)
        @@version = version
        return self
    end
end