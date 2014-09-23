using System;
using System.Collections.Generic;
using System.Collections.Specialized;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using GlobeLabsApi;

namespace GlobeLabsApiTest
{
    class Program
    {
        static void Main(string[] args)
        {
            L("GlobeLabs API Test Console");

            // Uncomment to Test Auth Service
            //TestAuthService();

            // Fetch accessToken and subscriber number to database.
            string accessToken = "SUBS_ACCESS_TOKEN";

            // Uncomment to Test SMS Service
            //TestSmsService(accessToken);

            // Uncomment to Test SMS Receive
            //TestSmsReceive(accessToken);

            // Uncomment to Test Payment Service
            //TestPaymentService(accessToken);
            
            L("Done.");
            Console.ReadLine();
        }

        static void L(string message)
        {
            Console.WriteLine(message);    
        }

        static void TestAuthService()
        {
            L("Auth Service Testing");

            // Fetch ApplicationId and ApplicationSecret in your config file
            string applicationId        = "YOUR_APP_ID";
            string applicationSecret    = "YOUR_APP_SECRET";

            GlobeLabs api = new GlobeLabs(applicationId, applicationSecret);

            string code = "CODE_PUSHED_BY_GLOBE_SYSTEM";

            // 1. Retrieve AccessToken based on Code.
            var result = api.Authorize(code);

            // Save the result to database, subscriber and access_token pair

            // Uncomment to get return values
            //string accessToken = result.Result.AccessToken;
            //string mobile = result.Result.SubscriberNumber;

            // 2. Generate Url to paste on your web browser
            //var dialogUrl = api.GetAuthLoginUrl();

            L("Data: " + result);
        }

        static void TestPaymentService(string accessToken)
        {
            L("Payment Service Testing");
            GlobeLabs api = new GlobeLabs(accessToken);

            var payload = new PaymentPayload
            {
                Amount = "0.00",
                Description = "Charging API",
                Number = "9171234567",
                ReferenceCode = "45750000001"
            };

            /*
             * TIPS for Reference Code:
             * - Fetch the latest count for payment reference in your database
             * - Store it in a variable or update your table that holds the last reference count
             * - IMPORTANT: Make sure that your reference code is unique. Bad Request will occur if code is repeating
             */
            // If your short code is 21554575, then your reference code prefix is 4575 + 7digit numbers

            var result = api.Charge(payload);
            L("Data: " + result);            
        }

        static void TestSmsService(string accessToken)
        {
            L("SMS Service Testing");
            GlobeLabs api = new GlobeLabs(accessToken);

            var numbers = new List<string>();
            numbers.Add("9171234567");
            // and add more numbers ...
            //numbers.Add("917XXXXXXX");

            var payload = new SmsPayload
            {
                Message = "Testing multiple recipients. Sms Service for Globe.",
                Numbers = numbers
            };

            var data = api.PushSms("SHORT_CODE - ex: 21589999", payload);
            L("Data: " + data);
        }

        static void TestSmsReceive(string accessToken)
        {
            GlobeLabs api = new GlobeLabs(accessToken);

            const string json = "{\"inboundSMSMessageList\":{\"inboundSMSMessage\":[{\"dateTime\":\"Fri Nov 22 2013 12:12:13 GMT+0000 (UTC)\",\"destinationAddress\":\"21581234\",\"messageId\":null,\"message\":\"Hello\",\"resourceURL\":null,\"senderAddress\":\"9171234567\"}],\"numberOfMessagesInThisBatch\":1,\"resourceURL\":null,\"totalNumberOfPendingMessages\":null}}";
            var data = api.GetIncomingMessage(json);

            L("Received SMS Messages: " + data.SmsMessageList.Message.InboundSMSMessage.Count());
            L("\tId: " + data.SmsMessageList.Message.InboundSMSMessage[0].MessageId);
            L("\tMessage: " + data.SmsMessageList.Message.InboundSMSMessage[0].Message);
            L("\tFrom: " + data.SmsMessageList.Message.InboundSMSMessage[0].SenderAddress);
            L("\tDate: " + data.SmsMessageList.Message.InboundSMSMessage[0].DateTime);

        }
    }
}
