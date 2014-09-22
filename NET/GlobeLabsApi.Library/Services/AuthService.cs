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
    class AuthService : GlobeLabsBase
    {
        private static readonly Lazy<AuthService> _instance = new Lazy<AuthService>(() => new AuthService());

        private AuthService() { }

        /// <summary>
        /// Gets the instance.
        /// </summary>
        /// <value>
        /// The instance.
        /// </value>
        public static AuthService Instance
        {
            get { return _instance.Value; }
        }

        /// <summary>
        /// Gets or sets the access token.
        /// </summary>
        /// <value>
        /// The access token.
        /// </value>
        public string AccessToken { get; set; }
        /// <summary>
        /// Gets or sets the subscriber number.
        /// </summary>
        /// <value>
        /// The subscriber number.
        /// </value>
        public string SubscriberNumber { get; set; }

        /// <summary>
        /// Gets the login dialog URL.
        /// </summary>
        /// <param name="appId">The application identifier.</param>
        /// <returns></returns>
        public string GetLoginDialogUrl(string appId)
        {
            return string.Format("{0}?app_id={1}", Config.DIALOG_OAUTH_ENDPOINT, appId);
        }

        /// <summary>
        /// Authorizes the specified code.
        /// </summary>
        /// <param name="code">The code.</param>
        /// <param name="appId">The application identifier.</param>
        /// <param name="appSecret">The application secret.</param>
        /// <returns></returns>
        public AuthResult Authorize(string code, string appId, string appSecret)
        {
            string url = Config.ACCESS_TOKEN_ENDPOINT;

            NameValueCollection parameter = new NameValueCollection();

            parameter.Add("app_id", appId);
            parameter.Add("app_secret", appSecret);
            parameter.Add("code", code);

            url = url + "?" + parameter.ToQueryString();

            string postData = string.Empty;
            string data = this.Post(url, Config.CONTENT_TYPE_FORM, postData);

            var result = new AuthResult();
            this.AccessToken = string.Empty;
            this.SubscriberNumber = string.Empty;

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
                var jsonResponse = JsonConvert.DeserializeObject<AuthResponse>(data);
                result.Result = jsonResponse;
                result.Status = this.ResponseStatus;

                this.AccessToken = jsonResponse.AccessToken;
                this.SubscriberNumber = jsonResponse.SubscriberNumber;
            }

            return result;
        }
    }
}
