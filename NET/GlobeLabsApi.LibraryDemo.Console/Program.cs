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

            PaymentPayload payload = new PaymentPayload();
            payload.Amount = "0.00";
            payload.Description = "Charging API";
            payload.Number = "9171234567";

            /*
             * TIPS for Reference Code:
             * - Fetch the latest count for payment reference in your database
             * - Store it in a variable or update your table that holds the last reference count
             * - IMPORTANT: Make sure that your reference code is unique. Bad Request will occur if code is repeating
             */
            // If your short code is 21554575, then your reference code prefix is 4575 + 7digit numbers
            payload.ReferenceCode = "45750000001";

            var result = api.Charge(payload);
            L("Data: " + result);            
        }

        static void TestSmsService(string accessToken)
        {
            L("SMS Service Testing");
            GlobeLabs api =new GlobeLabs(accessToken);

            var numbers = new List<string>();
            numbers.Add("9171234567");
            // and add more numbers ...
            //numbers.Add("917XXXXXXX");

            SmsPayload payload = new SmsPayload();
            payload.Message = "Testing multiple recipients. Sms Service for Globe.";
            payload.Numbers = numbers;

            var data = api.PushSms("SHORT_CODE - ex: 21589999", payload);
            L("Data: " + data);
        }
    }
}
