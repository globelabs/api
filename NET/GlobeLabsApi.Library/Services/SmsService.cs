using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Net;
using System.Text;
using System.Threading.Tasks;
using Newtonsoft.Json;

namespace GlobeLabsApi
{
    class SmsService : GlobeLabsBase
    {
        private static readonly Lazy<SmsService> _instance = new Lazy<SmsService>(() => new SmsService());

        private SmsService(){}

        /// <summary>
        /// Gets the instance.
        /// </summary>
        /// <value>
        /// The instance.
        /// </value>
        public static SmsService Instance
        {
            get { return _instance.Value; }
        }

        /// <summary>
        /// Sends the specified short code.
        /// </summary>
        /// <param name="shortCode">The short code.</param>
        /// <param name="accessToken">The access token.</param>
        /// <param name="payload">The payload.</param>
        /// <returns></returns>
        public SmsResult Send(string shortCode, string accessToken, SmsPayload payload)
        {
            string url = string.Format(Config.SMS_ENDPOINT, Config.API_VERSION, shortCode, accessToken);

            var dict = new Dictionary<string, object>();
            dict.Add("senderAddress", shortCode);
            dict.Add("outboundSMSTextMessage", new { message = payload.Message });
            dict.Add("address", payload.Numbers);

            var payloadObject = new { outboundSMSMessageRequest = dict };
            string jsonData = JsonConvert.SerializeObject(payloadObject);

            var result = new SmsResult();
            string data = this.Post(url, Config.CONTENT_TYPE_JSON, jsonData);

            if (data.Contains(Config.ERROR))
            {
                var jsonResponse = JsonConvert.DeserializeObject<ErrorResponse>(data);
                result.Result = null;
                result.Status = this.ResponseStatus;
                result.Status.StatusDescription = jsonResponse.Error;
                result.Status.StatusCode = (int)HttpStatusCode.BadRequest;
            }
            else
            {
                var jsonSms = string.IsNullOrEmpty(data) ? null : JsonConvert.DeserializeObject<SmsResponse>(data);
                result.Result = jsonSms;
                result.Status = this.ResponseStatus;
            }

            return result;
        } 
    }
}
