using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Newtonsoft.Json;

namespace GlobeLabsApi
{
    public class ChargingInformation
    {

        [JsonProperty("amount")]
        public string Amount { get; set; }

        [JsonProperty("currency")]
        public string Currency { get; set; }

        [JsonProperty("description")]
        public string Description { get; set; }
    }

    public class PaymentAmount
    {

        [JsonProperty("chargingInformation")]
        public ChargingInformation ChargingInformation { get; set; }

        [JsonProperty("totalAmountCharged")]
        public string TotalAmountCharged { get; set; }
    }

    public class AmountTransaction
    {

        [JsonProperty("endUserId")]
        public string EndUserId { get; set; }

        [JsonProperty("paymentAmount")]
        public PaymentAmount PaymentAmount { get; set; }

        [JsonProperty("referenceCode")]
        public string ReferenceCode { get; set; }

        [JsonProperty("serverReferenceCode")]
        public string ServerReferenceCode { get; set; }

        [JsonProperty("resourceURL")]
        public object ResourceURL { get; set; }
    }

    public class PaymentResponse
    {

        [JsonProperty("amountTransaction")]
        public AmountTransaction AmountTransaction { get; set; }
    }
}
