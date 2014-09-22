using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Newtonsoft.Json;

namespace GlobeLabsApi
{
    public class DeliveryInfoList
    {

        [JsonProperty("deliveryInfo")]
        public object[] DeliveryInfo;

        [JsonProperty("resourceURL")]
        public string ResourceURL;
    }

    public class OutboundSMSTextMessage
    {

        [JsonProperty("message")]
        public string Message;
    }

    public class ReciptRequest
    {

        [JsonProperty("notifyURL")]
        public string NotifyURL;

        [JsonProperty("callbackData")]
        public object CallbackData;

        [JsonProperty("senderName")]
        public object SenderName;

        [JsonProperty("resourceURL")]
        public string ResourceURL;
    }

    public class OutboundSMSMessageRequest
    {

        [JsonProperty("address")]
        public string Address;

        [JsonProperty("deliveryInfoList")]
        public DeliveryInfoList DeliveryInfoList;

        [JsonProperty("senderAddress")]
        public string SenderAddress;

        [JsonProperty("outboundSMSTextMessage")]
        public OutboundSMSTextMessage OutboundSMSTextMessage;

        [JsonProperty("reciptRequest")]
        public ReciptRequest ReciptRequest;
    }

    public class SmsResponse
    {

        [JsonProperty("outboundSMSMessageRequest")]
        public OutboundSMSMessageRequest OutboundSMSMessageRequest;
    }
}
