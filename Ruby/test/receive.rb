# In this example, we use rack to do a HTTP.
# You can use whatever library you want to handle HTTP Requests
require 'rack'
# We require also json for encoding the data
require 'json'

class Receive
    
    def call(env)
        result = String.new
		
        req = Rack::Request.new(env)
		
        # This is a catch for data that globe sends
        # whenever the subscriber replied to your app (shortcode)
        if req.post?
            esult = req.POST()
        end
		
        # This is a catch for data coming from
        # requesting access token via SMS
        if req.get?
            result = req.GET()
        end
		
        return [
            200,
            'Content-Type' => 'text/html'},
            [JSON.generate(result, quirks_mode: true)]
        ]
    end
	
end

# Let's create an HTTP to handle the POST data
# Here in this exampl, we use the same method 
# in getting access token and receive message
# we just separate it my method
# Globe is using GET method to send the access token
# while POST method is being used for receiving SMS
Rack::Handler::WEBrick.run(
    Receive.new,
    :Port => 9000
)
