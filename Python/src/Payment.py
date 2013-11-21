import Base
import urllib

class Payment(Base.Base):
	def __init__(self):
		super(Payment, self).__init__()

		self.url = self._error = {}
		self.url['charge'] = '%s/payment/%s/transactions/amount'

		self.error = {}
		self.error['token'] = 'Token is not defined'
		self.error['user'] = 'User is not defined'
		self.error['desc'] = 'Description undefined'
		self.error['amount'] = 'Amount undefined'
		self.error['reference'] = 'Reference is not define'

	def charge(self, token):
		""" Check for the required parameters """
		if not isinstance(token, str):
			raise Exception(self.error['token'])

		if not isinstance(self.recepient, str) and not isinstance(self.recepient, int):
			raise Exception(self.self.error['user'])

		if not isinstance(self.amount, str) and not isinstance(self.amount, int):
			raise Exception(self.error['amount'])

		if not isinstance(self.reference, str) and not isinstance(self.reference, int):
			raise Exception(self.error['reference'])

		""" Building the parameters """
		
		params = {}
		params['endUserId'] = self.recepient
		params['access_token'] = token
		params['amount'] = self.amount
		params['referenceCode'] = self.reference
		params['transactionOperationStatus'] = 'charged'

		url = self.url['charge'] % (self.host, self.version)
		
		return self.getResponse(url, 'post', params)

