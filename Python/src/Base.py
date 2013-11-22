import urllib
import requests
import json

class Base(object):

    def __init__(self):
        self.host = 'http://devapi.globelabs.com.ph'
        self.version = 'v1'

    """ For dynamic setting and getting of parameters """
    def __getattr__(self, name):

        def function(args = ''):

            if name[:3] == 'set':
                """ if name starts with 'set' ,
                it must be a setter """

                """ Get the rest of the name after 'set' word """
                var = name[3:].lower()
                """ Set variable as property of self object"""
                setattr(self, var, args) 

            elif name[:3] == 'get':
                """ if name starts with 'get' ,
                it must be a setter """

                """ Get the rest of the name after 'get' word """
                var = name[3:].lower()  
                """ Return property [ var ] of self object """
                return getattr(self, var)  

            return self

        return function

    def getResponse(self, url, type = 'get', params = {}, headers = {}):

        if type == 'delete':
            """ If type equals delete """
            response = requests.delete(url, data=params, headers=headers)

        elif type == 'post':
            """ If type equals post """
            response = requests.post(url, data=params, headers=headers)

        else:
            """ Else, Get request """
            response = requests.get(url, data=params, headers=headers)

        """ Set the reponse object as property of self object so they can retrieve it when they want """
        self.requestInfo = response

        try:
            data = json.loads(response.text)
        except:
            data = response.text
        
        """ Return raw text response """
        return data

