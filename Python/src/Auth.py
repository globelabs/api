import urllib
import requests
import Base

class Auth(Base.Base):
	def __init__(self, key, secret):
		super(Auth, self).__init__()

		self.key = key
		self.secret = secret

		self.url = {}
		self.url['login'] = 'http://developer.globelabs.com.ph/dialog/oauth?app_id=%s'
		self.url['token'] = 'http://developer.globelabs.com.ph/oauth/access_token?app_id=%s&app_secret=%s&code=%s'

	def getLoginUrl(self):
		""" Get the Oauth login url"""

		return self.url['login'] % self.key

	def getAccessToken(self, code, sms = False):
		""" Request for access token """
		if sms is True:
			if isinstance(code, dict) and isinstance(code['access_token'], str):
				return code
			else:
				return None

		url = self.url['token'] % (self.key, self.secret, code)
		return self.getResponse(url, 'post')

