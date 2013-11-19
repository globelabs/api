class Auth
    #Constants Variables
    ACCESS_TOKEN_URL    = 'http://developer.globelabs.com.ph/oauth/access_token'
    OAUTH_URL           = 'http://developer.globelabs.com.ph/dialog/oauth';
	
    #Class Variables
    @@appId             = String.new
    @@appSecret         = String.new
	
    # Initialize App ID and App Secret
    def initialize(appId, appSecret)
        @@appId      = appId
        @@appSecret  = appSecret
    end
	
    # Returns the login URL
    def getLoginUrl
        params = { 'app_id'	=> @@appId }
        query  = URI.encode_www_form(params)
		
        return OAUTH_URL+'?'+query
    end
	
    # Return the Access Token
    def getAccessToken(code)
        #Request Parameters
        params = {
            'app_id'      => @@appId,
            'app_secret'  => @@appSecret,
            'code'        => code
        }
		
        query        = URI.encode_www_form(params)
        requestUrl   = ACCESS_TOKEN_URL+'?'+query
		
        uri          = URI.parse(requestUrl)
        http         = Net::HTTP.new(uri.host, uri.port)
        request      = Net::HTTP::Post.new(requestUrl)
        response     = http.request(request)
		
        return response.body
    end
end