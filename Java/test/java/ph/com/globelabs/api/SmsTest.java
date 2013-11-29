package ph.com.globelabs.api;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpResponseFactory;
import org.apache.http.HttpVersion;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.DefaultHttpResponseFactory;
import org.apache.http.message.BasicStatusLine;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import ph.com.globelabs.api.exception.GlobeApiException;
import ph.com.globelabs.api.exception.ParameterRequiredException;
import ph.com.globelabs.api.request.HttpPostClient;
import ph.com.globelabs.api.response.InboundSmsMessage;
import ph.com.globelabs.api.response.SendSmsResponse;
import ph.com.globelabs.api.response.SmsResponse;

public class SmsTest {

    private Sms sms;

    @Before
    public void setUp() throws Exception {
        HttpPostClient client = new HttpPostClient() {
            @Override
            public HttpResponse execute(String requestUri) {
                try {
                    return mockHttpResponse();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return null;
            }
        };

        String shortCode = "9999";
        sms = new Sms(shortCode) {
            public Sms setClient(HttpPostClient client) {
                this.client = client;
                return this;
            }
        }.setClient(client);
    }

    @Test
    public void sendMessage() throws ClientProtocolException,
            UnsupportedEncodingException, IOException, JSONException,
            GlobeApiException, ParameterRequiredException {
        String subscriberNumber = "9173849494";
        String message = "Hello World";
        String accessToken = "_Ak28sdfl32r908sdf0q843qjlkjdf90234jlkasd98";

        SendSmsResponse response = sms.sendMessage(subscriberNumber,
                accessToken, message);
        assertEquals(201, response.getResponseCode());
        assertEquals("Created", response.getResponseMessage());
        assertEquals("Hello World", response.getMessage());
    }

    @Test
    public void getMessage() throws GlobeApiException {
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
        assertNotNull(response);

        assertEquals(1, response.getNumberOfMessagesInThisBatch());

        List<InboundSmsMessage> messages = response.getInboundSmsMessages();
        assertNotNull(messages);

        InboundSmsMessage message = messages.get(0);
        assertEquals("A B C D .E", message.getMessage());
        assertEquals("tel:+639173849494", message.getSenderAddress());
    }

    private HttpResponse mockHttpResponse()
            throws UnsupportedEncodingException, JSONException {
        HttpResponseFactory factory = new DefaultHttpResponseFactory();
        HttpResponse response = factory.newHttpResponse(new BasicStatusLine(
                HttpVersion.HTTP_1_1, 201, "Created"), null);
        response.setHeader("Content-Type", "application/json");

        JSONObject responseObject = new JSONObject();
        responseObject.put("success", "true");
        responseObject.put("message", "Hello World");
        responseObject.put("address", "9173849494");
        responseObject.put("senderAddress", "9999");
        responseObject.put("access_token",
                "_Ak28sdfl32r908sdf0q843qjlkjdf90234jlkasd98");

        StringEntity stringEntity = new StringEntity(responseObject.toString());
        stringEntity.setContentType("application/json");

        response.setEntity(stringEntity);

        return response;
    }

}
