package ph.com.globelabs.api.response;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SmsResponse {

    private List<InboundSmsMessage> inboundSmsMessages;
    private int numberOfMessagesInThisBatch;
    private String resourceURL;
    private int totalNumberOfPendingMessages;

    public SmsResponse(String rawBody) throws JSONException {
        JSONObject body = new JSONObject(rawBody);

        JSONObject inboundSMSMessageList = body
                .getJSONObject("inboundSMSMessageList");

        resourceURL = (inboundSMSMessageList.getString("resourceURL") != "null") ? inboundSMSMessageList
                .getString("resourceURL") : null;
        numberOfMessagesInThisBatch = (inboundSMSMessageList
                .getString("numberOfMessagesInThisBatch") != null) ? inboundSMSMessageList
                .getInt("numberOfMessagesInThisBatch") : 0;
        totalNumberOfPendingMessages = (inboundSMSMessageList
                .getString("totalNumberOfPendingMessages") != null) ? inboundSMSMessageList
                .getInt("totalNumberOfPendingMessages") : 0;

        JSONArray inboundSmsMessages = inboundSMSMessageList
                .getJSONArray("inboundSMSMessage");

        this.inboundSmsMessages = new ArrayList<InboundSmsMessage>();
        for (int i = 0; i < inboundSmsMessages.length(); i++) {
            InboundSmsMessage sms = new InboundSmsMessage(
                    inboundSmsMessages.getJSONObject(i));
            this.inboundSmsMessages.add(sms);
        }
    }

    public List<InboundSmsMessage> getInboundSmsMessages() {
        return inboundSmsMessages;
    }

    public int getNumberOfMessagesInThisBatch() {
        return numberOfMessagesInThisBatch;
    }

    public String getResourceURL() {
        return resourceURL;
    }

    public int getTotalNumberOfPendingMessages() {
        return totalNumberOfPendingMessages;
    }

    @Override
    public String toString() {
        return "SmsResponse [inboundSmsMessages=" + inboundSmsMessages
                + ", numberOfMessagesInThisBatch="
                + numberOfMessagesInThisBatch + ", resourceURL=" + resourceURL
                + ", totalNumberOfPendingMessages="
                + totalNumberOfPendingMessages + "]";
    }

}
