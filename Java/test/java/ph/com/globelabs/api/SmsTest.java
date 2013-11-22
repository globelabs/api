package ph.com.globelabs.api;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

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

import ph.com.globelabs.api.Sms;
import ph.com.globelabs.api.exception.GlobeApiException;
import ph.com.globelabs.api.exception.ParameterRequiredException;
import ph.com.globelabs.api.request.HttpPostClient;
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
        
        SendSmsResponse response = sms.sendMessage(subscriberNumber, accessToken, message);
        assertEquals(201, response.getResponseCode());
        assertEquals("Created", response.getResponseMessage());
        assertEquals("Hello World", response.getMessage());
    }
    
    @Test
    public void getMessage() {
        String rawBody = "command_length=71&command_id=5&command_status=0&sequence_number=70&"
                + "command=deliver_sm&service_type=&source_addr_ton=2&source_addr_npi=1&"
                + "source_addr=9173849494&dest_addr_ton=4&dest_addr_npi=9&destination_addr=21589999&"
                + "esm_class=0&protocol_id=0&priority_flag=0&schedule_delivery_time=&validity_period=&"
                + "registered_delivery=0&replace_if_present_flag=0&data_coding=0&sm_default_msg_id=0&"
                + "short_message[message]=&source_network_type=1&dest_network_type=1&"
                + "message_payload[message]=A%20%20B%20C%20D%20.E";
        SmsResponse response = sms.getMessage(rawBody);

        assertEquals("A  B C D .E", response.getMessage());
        assertEquals("9173849494", response.getSourceAddr());
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
