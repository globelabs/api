require './../src/GlobeApi.rb'
require './../src/Sms.rb'
require 'rack'

appId = 'MeBKgt9nRb8tBginqMTR6gtEaB8BtAb7'
appSecret = '149900c1de054210631719796413d2b0d5d723d8fefed1dda9cac21495a09054'
code = 'k95U8AraXfejdx6Hg6oy8Sae46GHa4yqMtzXaB5SqLR8aIL54goUq5RBGSGg49bFBd5kpUM8Rj6ugxeoptLxer9CLAjxXFLEG5BCLprRAsXkb4rh6kqyjsnE97jF5bcbXGi9yAFzMqoxs47bMGh9Xr6EsoRGo9CdqjeBFpdejLCaLeKkt4ARL7uBM58AUj5465F6kRG7SMe4nMU9pREaI4GaGXSbAye4tBR4rxH4joXjSk4dnzHBnro5f6BkaxUA'
accessToken = 'q3JYrRuW_4_KglWC20iRmk5qNaK3_-o1xdwipACY6dA'
shortcode = '3651'
subscriber = '09175646847'


# Initialize GlobeApi Class
globe = GlobeApi.new()

###### Authentication #######
auth = globe.auth(appId, appSecret)

# Get Login URL
#puts auth.getLoginUrl

# Get Access Token
#puts auth.getAccessToken(code)

###### SMS ######
# Send SMS (Outbound)
#puts globe.sms(shortcode)
#	.sendMessage(accessToken, subscriber, 'hello')

# Charge Subscriber
puts globe.payment(accessToken, subscriber)
	.charge('0.00', '36511000009')

data = '{"test": "dan"}'

response = globe.sms(5416).getMessage(data)
puts response['test']
#puts 'da'