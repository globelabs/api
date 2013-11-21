"You should do this if your code is"
"separated from the globe api code"
import sys
import os
lib_path = os.path.abspath('../src')
sys.path.append(lib_path)

import GlobeApi
api = GlobeApi.GlobeApi('v1')

"----------Authentication ----------------------"
oauth = api.oAuth('key-here', 'secret-here')

#get the login url
print oauth.getLoginUrl()

#get access token
print oauth.getAccessToken('code-here')


"--------- Send Message ------------------------"
sms = api.sms('202')
sms.setRecepient('your-number')
sms.setMessage('message here')
print sms.send('token-here')

"--------- Payment Charge Request ---------------"
payment = api.payment('reference-id')
payment.setAmount(1)
payment.setRecepient('your-number')
print payment.charge('token-here')