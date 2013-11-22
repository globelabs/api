require 'rack'
require 'json'

class Receive
	
    def call(env)
        result = String.new
		
        req = Rack::Request.new(env)
		
        # This is a catch for data that globe sends
        # whenever the subscriber replied
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

Rack::Handler::WEBrick.run(
    Receive.new,
    :Port => 9000
)
