import sys
import json
import cgi

#handling POST and GET data
raw_data = sys.stdin.read()
try:
	#check first if its json data
	#and try to decode it using json.load
	data = json.loads(raw_data)
except:
	#if it fails, get the request fields
	data = cgi.FieldStorage()

#do what ever you want with the data here
print data