"You should do this if your code is"
"separated from the globe api code"
import sys
import os
lib_path = os.path.abspath('../src')
sys.path.append(lib_path)

import GlobeApi
api = GlobeApi.GlobeApi('v1')

"----------Authentication ----------------------"
#auth = api.oAuth('MeBKgt9nRb8tBginqMTR6gtEaB8BtAb7', '149900c1de054210631719796413d2b0d5d723d8fefed1dda9cac21495a09054')

#get the login url
#print auth.getLoginUrl()

#get access token
#code = {}
#code['access_token'] = 'r5GSB885gfapxBpfXj64ruRro6Xs9yq9ehyqbyEUr8qKRCrB4Kxuoe96dCgr58xHd56jbfB7bqeSKrkyoCdgdAgCzgzGMFEqAn7CLMnLBFe84gyIaqr9bU59X4XH9dcMyeiXAeH4ErnbUqa4xBIyMnGLFe6AkxC7kzM5FyedLBCd9kqgC6ybXbSG66bXf8z5ypHeL997CgG4BRuAeqBnCdyb5oUq5qGkhK9oj9sej6E7u4dxyBf4x8qKfp8rXESR';
#a = auth.getAccessToken(code['access_token'])
#print a['access_token']
#token = 'ZlQD_rQWayPbiYbqe0g656-mfoUISbmPg2g4u6m0PzY'

"--------- Send Message ------------------------"
#sms = api.sms('3651')
#sms.setRecepient('9268292535')
#sms.setCorrelator('[client-correlator]') """Optional"""
#print sms.send(token)

"------------Sample Receiving Code ------------------------"
#print sms.getMessages('[registration-ID]')

"--------- Payment Charge Request ---------------"
#payment = api.payment('44481000006')
#payment.setAmount('0.00')
#payment.setRecepient('9176234236')
#print payment.charge('X1obYywA-O5GRAEv4vEUrk-MvJF6P3uNkgLQS3roQUw')
