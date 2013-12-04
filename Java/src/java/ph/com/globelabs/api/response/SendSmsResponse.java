package ph.com.globelabs.api.response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * This object is created from the expected response of the server.
 * 
 * This response also has a responseCode, responseMessage, and holds the raw
 * HttpResponse. See {@link Response}.
 * 
 */
public class SendSmsResponse extends Response {

    private String message;
    private String address;
    private List<String> deliveryInfo;
    private String senderAddress;
    private String clientCorrelator;
    private String notifyURL;
    private String callbackData;
    private String senderName;
    private String resourceURL;
    private String error;

    public SendSmsResponse(HttpResponse httpResponse)
            throws IllegalStateException, JSONException, IOException {
        super(httpResponse);

        JSONObject responseContent = new JSONObject(super.getContent());

        JSONObject outboundSMSMessageRequest = responseContent
                .has("outboundSMSMessageRequest") ? responseContent
                .getJSONObject("outboundSMSMessageRequest") : null;
        if (outboundSMSMessageRequest != null) {
            this.address = outboundSMSMessageRequest.has("address") ? outboundSMSMessageRequest
                    .getString("address") : null;

            JSONObject deliveryInfo = outboundSMSMessageRequest
                    .has("deliveryInfoList") ? outboundSMSMessageRequest
                    .getJSONObject("deliveryInfoList") : null;
            this.deliveryInfo = new ArrayList<String>();
            if (deliveryInfo != null) {
                JSONArray deliveryInfoList = deliveryInfo.has("deliveryInfo") ? deliveryInfo
                        .getJSONArray("deliveryInfo") : null;
                for (int i = 0; i < deliveryInfoList.length(); i++) {
                    this.deliveryInfo.add(deliveryInfoList.getString(i));
                }
            }

            JSONObject outboundSMSTextMessage = outboundSMSMessageRequest
                    .has("outboundSMSTextMessage") ? outboundSMSMessageRequest
                    .getJSONObject("outboundSMSTextMessage") : null;
            if (outboundSMSTextMessage != null) {
                this.message = outboundSMSTextMessage.has("message") ? outboundSMSTextMessage
                        .getString("message") : null;
            }

            this.senderAddress = outboundSMSMessageRequest.has("senderAddress") ? outboundSMSMessageRequest
                    .getString("senderAddress") : null;
            this.clientCorrelator = outboundSMSMessageRequest
                    .has("clientCorrelator") ? outboundSMSMessageRequest
                    .getString("clientCorrelator") : null;

            JSONObject receiptRequest = outboundSMSMessageRequest
                    .has("reciptRequest") ? outboundSMSMessageRequest
                    .getJSONObject("reciptRequest") : null;
            if (receiptRequest != null) {
                this.notifyURL = receiptRequest.has("notifyURL") ? receiptRequest
                        .getString("notifyURL") : null;
                this.callbackData = receiptRequest.has("callbackData") ? receiptRequest
                        .getString("callbackData") : null;
                this.senderName = receiptRequest.has("senderName") ? receiptRequest
                        .getString("senderName") : null;
                this.resourceURL = receiptRequest.has("resourceURL") ? receiptRequest
                        .getString("resourceURL") : null;
            }
        }

        if (responseContent.has("error")) {
            this.error = responseContent.getString("error");
        }
    }

    public SendSmsResponse(int statusCode, String reasonPhrase) {
        super(statusCode, reasonPhrase);
    }

    public String getMessage() {
        return message;
    }

    public String getAddress() {
        return address;
    }

    public List<String> getDeliveryInfo() {
        return deliveryInfo;
    }

    public String getSenderAddress() {
        return senderAddress;
    }

    public String getClientCorrelator() {
        return clientCorrelator;
    }

    public String getNotifyURL() {
        return notifyURL;
    }

    public String getCallbackData() {
        return callbackData;
    }

    public String getSenderName() {
        return senderName;
    }

    public String getResourceURL() {
        return resourceURL;
    }

    public String getError() {
        return error;
    }

    @Override
    public String toString() {
        if (error == null) {
            return "SendSmsResponse [message=\"" + message + "\", address="
                    + address + ", deliveryInfo=" + deliveryInfo
                    + ", senderAddress=" + senderAddress
                    + ", clientCorrelator=" + clientCorrelator + ", notifyURL="
                    + notifyURL + ", callbackData=" + callbackData
                    + ", senderName=" + senderName + ", resourceURL="
                    + resourceURL + "] " + super.toString();
        } else {
            return "SendSmsResponse [error=\"" + error + "\"] "
                    + super.toString();
        }
    }

}
