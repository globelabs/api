package ph.com.globelabs.api.response;

import org.json.JSONException;
import org.json.JSONObject;

public class InboundSmsMessage {

    private String dateTime;
    private String destinationAddress;
    private String messageId;
    private String message;
    private String resourceURL;
    private String senderAddress;

    public InboundSmsMessage(JSONObject inboundSmsMessage) throws JSONException {
        dateTime = (inboundSmsMessage.getString("dateTime") != "null") ? inboundSmsMessage
                .getString("dateTime") : null;
        destinationAddress = (inboundSmsMessage.getString("destinationAddress") != "null") ? inboundSmsMessage
                .getString("destinationAddress") : null;
        messageId = (inboundSmsMessage.getString("messageId") != "null") ? inboundSmsMessage
                .getString("messageId") : null;
        message = (inboundSmsMessage.getString("message") != "null") ? inboundSmsMessage
                .getString("message") : null;
        resourceURL = (inboundSmsMessage.getString("resourceURL") != "null") ? inboundSmsMessage
                .getString("resourceURL") : null;
        senderAddress = (inboundSmsMessage.getString("senderAddress") != "null") ? inboundSmsMessage
                .getString("senderAddress") : null;
    }

    public String getDateTime() {
        return dateTime;
    }

    public String getDestinationAddress() {
        return destinationAddress;
    }

    public String getMessageId() {
        return messageId;
    }

    public String getMessage() {
        return message;
    }

    public String getResourceURL() {
        return resourceURL;
    }

    public String getSenderAddress() {
        return senderAddress;
    }

    @Override
    public String toString() {
        return "InboundSmsMessage [senderAddress=" + senderAddress
                + ", destinationAddress=" + destinationAddress + ", message="
                + message + ", dateTime=" + dateTime + ", messageId="
                + messageId + ", resourceURL=" + resourceURL + "]";
    }

}
