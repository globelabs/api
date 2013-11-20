class Sms
    # CONSTANTS
    URL = 'http://devapi.globelabs.com.ph/smsmessaging/%s/outbound/%s/requests'
	
    # CLASS VARIABLES
    @@accessToken    	= String.new
    @@shortCode      	= String.new
	@@params			= Array.new
	@@version			= 'v1'
	
    # Initialize Access Token and Short Code
    def initialize(accessToken, shortCode)
        @@accessToken  	= accessToken
		@@shortCode		= shortCode
    end
	
	def outbound(subscriber, message)
		#POST Parameters
        @@params = {
            'address'  => subscriber,
            'message'  => message
        }
		
		return self
	end
	
	def inbound(request)
		return JSON.parse request
	end
	
	def setVersion(version)
		@@version = version
		return self
	end
	
    # Sends an SMS
    #
    # * Sets the string query for the parameters
	# * Sends data using POST METHOD
    def send
        #Build HTTP parameters for access token
        params  = { 'access_token' => @@accessToken }
        query   = URI.encode_www_form(params)
		
        #Setting up the request URL
        requestUrl = sprintf(URL, @@version, @@shortCode)
        requestUrl = requestUrl+'?'+query
		
        #Request as POST METHOD
        uri        = URI.parse(requestUrl)
        http       = Net::HTTP.new(uri.host, uri.port)
        request    = Net::HTTP::Post.new(requestUrl)
		
        request.set_form_data(@@params)
		
        response 	= http.request(request)
		
        return response.body
    end
end