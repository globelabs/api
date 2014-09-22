using System;
using System.Collections.Generic;
using System.Collections.Specialized;
using System.IO;
using System.Linq;
using System.Net;
using System.Text;
using System.Threading.Tasks;
using Newtonsoft.Json;

namespace GlobeLabsApi
{
    class PaymentService : GlobeLabsBase
    {
        private static readonly Lazy<PaymentService> _instance = new Lazy<PaymentService>(() => new PaymentService());

        /// <summary>
        /// Prevents a default instance of the <see cref="PaymentService"/> class from being created.
        /// </summary>
        private PaymentService()
        {
        }

        /// <summary>
        /// Gets the instance.
        /// </summary>
        /// <value>
        /// The instance.
        /// </value>
        public static PaymentService Instance
        {
            get { return _instance.Value; }
        }


        /// <summary>
        /// Charges the specified Payment Payload.
        /// </summary>
        /// <param name="payload">The payload.</param>
        /// <param name="accessToken">The access token.</param>
        /// <returns></returns>
        public PaymentResult Charge(PaymentPayload payload, string accessToken)
        {
            string url = string.Format(Config.PAYMENT_ENDPOINT, Config.API_VERSION);

            var collection = new NameValueCollection
            {
                { "amount", payload.Amount },
                { "description", payload.Description },
                { "endUserId", payload.Number },
                { "referenceCode", payload.ReferenceCode},
                { "access_token", accessToken},
                { "transactionOperationStatus", Config.TRANSACTION_CHARGED }
            };

            string data = this.Post(url, Config.CONTENT_TYPE_FORM, collection);
            
            var result = new PaymentResult();

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
                var jsonPayment = string.IsNullOrEmpty(data) ? null : JsonConvert.DeserializeObject<PaymentResponse>(data);
                result.Result = jsonPayment;
                result.Status = this.ResponseStatus;
            }

            return result;
        }
    }
}
