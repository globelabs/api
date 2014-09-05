using System;
using System.Collections.Generic;
using System.Collections.Specialized;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Web;

namespace GlobeLabsApi
{
    public static class StringExtensions
    {
        /// <summary>
        /// NamedValueCollection to QueryString data
        /// </summary>
        /// <param name="collection">The collection.</param>
        /// <returns></returns>
        public static string ToQueryString(this NameValueCollection collection)
        {
            string query = string.Join("&", collection.AllKeys.Select(key => string.Format("{0}={1}", key, HttpUtility.UrlEncode(collection[key]))));     
            query = string.Join("&", collection.AllKeys.Where(key => !string.IsNullOrWhiteSpace(collection[key])).Select(key => string.Format("{0}={1}", key, HttpUtility.UrlEncode(collection[key]))));

            return query;

        }
    }
}
