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
    internal abstract class GlobeLabsBase
    {
        public HttpStatus ResponseStatus { get; set; }

        public string Post(string requestUrl, string contentType, string postData)
        {
            var request = (HttpWebRequest)WebRequest.Create(requestUrl);
            request.Method = "POST";
            request.ContentType = contentType;

            byte[] bytes = Encoding.UTF8.GetBytes(postData);
            request.ContentLength = bytes.Length;

            Stream requestStream = request.GetRequestStream();
            requestStream.Write(bytes, 0, bytes.Length);

            string result = string.Empty;
            try
            {
                using (WebResponse webResponse = request.GetResponse())
                {
                    result = webResponse.ReadResponse();
                    this.ResponseStatus = webResponse.ResponseStatus();
                }
            }
            catch (WebException ex)
            {
                using (WebResponse response = ex.Response)
                {
                    HttpWebResponse httpResponse = (HttpWebResponse)response;
                    this.ResponseStatus = httpResponse.ResponseStatus();
                }
            }

            return result;
        }

        public string Post(string requestUrl, string contentType, NameValueCollection collection)
        {
            string postData = collection != null ? collection.ToQueryString() : string.Empty;
            var result = Post(requestUrl, contentType, postData);

            return result;
        }
    }
}
