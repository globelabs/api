using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace GlobeLabsApi
{
    internal static class Config
    {
        public const string API_VERSION = "v1";
        public const string SMS_ENDPOINT = "http://devapi.globelabs.com.ph/smsmessaging/{0}/outbound/{1}/requests?access_token={2}";
        public const string PAYMENT_ENDPOINT = "http://devapi.globelabs.com.ph/payment/{0}/transactions/amount";

        public const string ACCESS_TOKEN_ENDPOINT = "http://developer.globelabs.com.ph/oauth/access_token";
        public const string DIALOG_OAUTH_ENDPOINT = "http://developer.globelabs.com.ph/dialog/oauth";

        public const string CONTENT_TYPE_JSON = "application/json";
        public const string CONTENT_TYPE_FORM = "application/x-www-form-urlencoded";

        public const string ERROR = "error";
        public const string TRANSACTION_CHARGED = "charged";
    }
}
