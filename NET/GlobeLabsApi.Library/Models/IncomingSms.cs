using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Newtonsoft.Json;

namespace GlobeLabsApi
{
    public class InboundSMSMessage
    {

        [JsonProperty("dateTime")]
        public string DateTime { get; set; }

        [JsonProperty("destinationAddress")]
        public string DestinationAddress { get; set; }

        [JsonProperty("messageId")]
        public object MessageId { get; set; }

        [JsonProperty("message")]
        public string Message { get; set; }

        [JsonProperty("resourceURL")]
        public object ResourceURL { get; set; }

        [JsonProperty("senderAddress")]
        public string SenderAddress { get; set; }
    }

    public class InboundSMSMessageList
    {

        [JsonProperty("inboundSMSMessage")]
        public InboundSMSMessage[] InboundSMSMessage { get; set; }

        [JsonProperty("numberOfMessagesInThisBatch")]
        public int NumberOfMessagesInThisBatch { get; set; }

        [JsonProperty("resourceURL")]
        public object ResourceURL { get; set; }

        [JsonProperty("totalNumberOfPendingMessages")]
        public object TotalNumberOfPendingMessages { get; set; }
    }

    public class SmsIncomingResponse
    {

        [JsonProperty("inboundSMSMessageList")]
        public InboundSMSMessageList Message { get; set; }
    }
}
