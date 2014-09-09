using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Net;
using System.Text;
using System.Threading.Tasks;

namespace GlobeLabsApi
{
    public static class HttpExtensions
    {
        public static string ReadResponse(this WebResponse webResponse)
        {
            if (webResponse == null)
                return string.Empty;

            using (var responseStream = webResponse.GetResponseStream())
            using (var responseReader = new StreamReader(responseStream))
            {
                var responseBody = responseReader.ReadToEnd();
                return responseBody;
            }
        }

        public static HttpStatus ResponseStatus(this WebResponse webResponse)
        {
            HttpWebResponse httpResponse = (HttpWebResponse)webResponse;

            return new HttpStatus
            {
                StatusCode = (int) httpResponse.StatusCode,
                StatusDescription = httpResponse.StatusDescription
            };
        }
    }
}
