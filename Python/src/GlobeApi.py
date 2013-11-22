import Sms
import Payment
import Base
import Auth

class GlobeApi(object):

    def __init__(self, v = 'v1'):
        super(GlobeApi, self).__init__()
        self.version = v

    def sms(self, code):
        sms = Sms.Sms()
        sms.setVersion(self.version)
        sms.setSender(code)
        return sms

    def payment(self, ref):
        payment = Payment.Payment()
        payment.setReference(ref)
        return payment

    def oAuth(self, key, secret):
        return Auth.Auth(key, secret)

