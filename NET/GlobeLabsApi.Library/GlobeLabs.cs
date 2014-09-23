using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace GlobeLabsApi
{
    public class GlobeLabs
    {
        private string applicationId = string.Empty;
        private string applicationSecret = string.Empty;

        /// <summary>
        /// Initializes a new instance of the <see cref="GlobeLabs"/> class.
        /// </summary>
        public GlobeLabs(string accessToken)
        {
            this.AccessToken = accessToken;
        }

        /// <summary>
        /// Gets or sets the access token.
        /// </summary>
        /// <value>
        /// The access token.
        /// </value>
        public string AccessToken { get; set; }

        /// <summary>
        /// Initializes a new instance of the <see cref="GlobeLabs"/> class.
        /// </summary>
        /// <param name="applicationId">The application identifier.</param>
        /// <param name="applicationSecret">The application secret.</param>
        public GlobeLabs(string applicationId, string applicationSecret)
        {
            this.applicationId = applicationId;
            this.applicationSecret = applicationSecret;
        }

        /// <summary>
        /// Authorizes the specified code.
        /// </summary>
        /// <param name="code">The code.</param>
        /// <returns></returns>
        public AuthResult Authorize(string code)
        {
            var authResult = AuthService.Instance.Authorize(code, this.applicationId, this.applicationSecret);
            this.AccessToken = AuthService.Instance.AccessToken;

            return authResult;
        }

        /// <summary>
        /// Gets the authentication login URL.
        /// </summary>
        /// <returns></returns>
        public string GetAuthLoginUrl()
        {
            if (string.IsNullOrEmpty(this.applicationId))
            {
                throw new ArgumentException("getauthloginurl - applicationId is required.");
            }

            return AuthService.Instance.GetLoginDialogUrl(this.applicationId);
        }

        /// <summary>
        /// Charges the specified payload.
        /// </summary>
        /// <param name="payload">The payload.</param>
        /// <returns></returns>
        public PaymentResult Charge(PaymentPayload payload)
        {
            return PaymentService.Instance.Charge(payload, this.AccessToken);
        }


        /// <summary>
        /// Pushes the SMS.
        /// </summary>
        /// <param name="shortCode">The short code.</param>
        /// <param name="payload">The payload.</param>
        /// <returns></returns>
        public SmsResult PushSms(string shortCode, SmsPayload payload)
        {
            return SmsService.Instance.Send(shortCode, this.AccessToken, payload);
        }

        /// <summary>
        /// Gets the incoming message from the Raw JSON body that your system fetched from NotifyURL
        /// </summary>
        /// <param name="json">The json.</param>
        /// <returns>SmsIncomingResult object</returns>
        public SmsIncomingResult GetIncomingMessage(string json)
        {
            return SmsService.Instance.ReceiveMessageToMessageList(json);
        }
    }
}
