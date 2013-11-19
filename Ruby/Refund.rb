class Refund
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
	
	# Request a Transaction Refund
    #
    # * Sets the string query for the parameters
	# * Sends data using POST METHOD
    def send
        #Request as POST METHOD
        uri        = URI.parse(URL)
        http       = Net::HTTP.new(uri.host, uri.port)
        request    = Net::HTTP::Post.new(uri.path)
		
        params  = {
			'access_token'                => @@accessToken,
			'endUserId'                   => '9175646847',
			'amount'                     => '1',
			'referenceCode'				=> '1234'
		}
		
        request.set_form_data(params)
		request['Accept'] = 'application/json'
        response 	= http.request(request)
		
        return response.body
    end
end