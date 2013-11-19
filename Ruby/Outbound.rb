class Outbound
    # CONSTANTS
    URL = 'http://devapi.globelabs.com.ph/smsmessaging/v1/outbound/%s/requests'
	
    # CLASS VARIABLES
    @@accessToken    = String.new
    @@shortCode      = String.new
	
    # Initialize Access Token and Short Code
    def initialize(accessToken, shortCode)
        @@accessToken  = accessToken
        @@shortCode    = shortCode
    end
	
    # Sends an SMS
    #
    # * Sets the string query for the parameters
	# * Sends data using POST METHOD
    def send(subscriber, message)
        #Build HTTP parameters
        params  = { 'access_token' => @@accessToken }
        query   = URI.encode_www_form(params)
		
        #Setting up the request URL
        requestUrl = sprintf(URL, @@shortCode)
        requestUrl = requestUrl+'?'+query
		
        #Request as POST METHOD
        uri        = URI.parse(requestUrl)
        http       = Net::HTTP.new(uri.host, uri.port)
        request    = Net::HTTP::Post.new(requestUrl)
		
        #POST Parameters
        params = {
            'address'  => subscriber,
            'message'  => message
        }
		
        request.set_form_data(params)
		
        response 	= http.request(request)
		
        return response.body
    end
end