require './../src/GlobeApi.rb'

# Initialize GlobeApi Class
globe = GlobeApi.new()

###### Authentication #######
auth = globe.auth([APP_ID], [APP_SECRET])

# Get Login URL
puts auth.getLoginUrl

# Get Access Token
puts auth.getAccessToken([CODE])

###### SMS ######
# Send SMS (Outbound)
puts globe.sms([SHORTCODE])
	.sendMessage([YOUR_ACCESS_TOKEN], [SUBSCRIBER_NUMBER], [MESSAGE])

# Charge Subscriber
puts globe.payment([YOUR_ACCESS_TOKEN], [SUBSCRIBER_NUMBER])
	.charge([AMOUNT], [REFERENCE_NUMBER])