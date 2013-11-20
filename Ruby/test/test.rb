require './../src/GlobeApi.rb'

# Initialize GlobeApi Class
globe = GlobeApi.new()

###### Authentication #######
#auth = globe.auth([APP_ID], [APP_SECRET])

# Get Login URL
#puts auth.getLoginUrl

# Get Access Token
#puts auth.getAccessToken([CODE])


###### SMS ######
# Send SMS (Outbound)
puts globe.sms([YOUR_ACCESS_TOKEN], [SHORTCODE])
	.outbound([SUBSCRIBER_NUMBER], [MESSAGE])
	.send

# Charge Subscriber
puts globe.payment([YOUR_ACCESS_TOKEN])
	.charge([SUBSCRIBER_NUMBER], [AMOUNT], [REFERENCE_NUMBER])
	.send