import sms
import oauth
import payment

############################ Outbound ###########################
send = sms.Sms()

#-------------------------- SEND SMS -------------------------------#
#send.setHost('http://devapi.globelabs.com.ph') #set host for api
#send.setVersion('v1')
#send.setAddress('9268292535') #set address to send message to
#send.setSender('4153') #set sender address
#send.setMsg('hey') #set message
#print send.send('iv8i_nWb4SGBKBEHTSVCrjKpN32-7d-WV7AVn_pIGxQ') #send request


#check http request info
#print send.requestInfo

########################## Oauth ##################
#auth = oauth.Oauth('kr4daHL4RKpu4RTkzTRaxuGz4gGHGA4a', 'd644537ea61336b6930d5bcca1dbc022a04c3a246cc5143111de3bab7537fa49')

#------------------------- Get Request Url -----------------------------------------
#print auth.getLoginUrl()

#------------------------- Get Access Token ---------------------------------------
#code = 'pqdFEBenjhRz94gu6ejzXfepRqMFgkAR9hpd98XIGnGA4upMR4AfEAaRAfX74p6S7rBExCzap9KfjqzoEU5n6d9hRBnBjUXAGxpFjGA5kSBeR6bFK9yGRIe7BEoC7kcRobiB8GCdXy7BInpR7LF7oAEgSExGg4Fedno5U5o6pkh6EzxnUeLpkzfXrBydCGr4AbSjKaKofA5R87fgXGbyu869odIMEAG5hApRXyFGajgafA69pKuRgeexhyApAEF4'
#print auth.getAccessToken(code)

############################# Payment ###############################################
payment = payment.Payment()

#--------------------------- Charge ----------------------------------------------#
#payment.setAmount(1)
#payment.setDescription('test description')
#payment.setUser('9268292535')
#payment.setReference('quetery12')
#print payment.charge('iv8i_nWb4SGBKBEHTSVCrjKpN32-7d-WV7AVn_pIGxQ')