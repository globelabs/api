package ph.com.globelabs.api.sample;

import java.util.List;

import ph.com.globelabs.api.Sms;
import ph.com.globelabs.api.exception.GlobeApiException;
import ph.com.globelabs.api.exception.ParameterRequiredException;
import ph.com.globelabs.api.response.InboundSmsMessage;
import ph.com.globelabs.api.response.SmsResponse;

public class SmsRunner {

    public static void main(String[] args) throws GlobeApiException,
            ParameterRequiredException {
        String shortCode = "9999";
        Sms sms = new Sms(shortCode);

        String subscriberNumber = "9173849494";
        String accessToken = "_Ak28sdfl32r908sdf0q843qjlkjdf90234jlkasd98";
        String message = "Hello World";
        String clientCorrelator = "9999" + System.currentTimeMillis();
        
        System.out.println(sms.sendMessage(subscriberNumber, accessToken,
                message));
        System.out.println(sms.sendMessage(subscriberNumber, accessToken,
                message, clientCorrelator));

        String rawBody = "{\"inboundSMSMessageList\":{\""
                + "inboundSMSMessage\":[{\"dateTime\""
                + ":\"Fri Nov 29 2013 00:16:17 GMT+0000 (UTC)\","
                + "\"destinationAddress\":\"tel:21589999\","
                + "\"messageId\":\"5297dcd17b8a4ead5f000032\","
                + "\"message\":\"A B C D .E\",\"resourceURL\":null,"
                + "\"senderAddress\":\"tel:+639173849494\"}],"
                + "\"numberOfMessagesInThisBatch\":1,"
                + "\"resourceURL\":null,"
                + "\"totalNumberOfPendingMessages\":0}}";
        SmsResponse response = sms.getMessage(rawBody);
        List<InboundSmsMessage> messages = response.getInboundSmsMessages();
        InboundSmsMessage inboundMessage = messages.get(0);
        
        System.out.println(inboundMessage.getMessage());
    }

}
