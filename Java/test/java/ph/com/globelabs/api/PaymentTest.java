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

import ph.com.globelabs.api.exception.GlobeApiException;
import ph.com.globelabs.api.exception.ParameterRequiredException;
import ph.com.globelabs.api.request.HttpPostClient;
import ph.com.globelabs.api.response.ChargeUserResponse;

public class PaymentTest {

    private Payment payment;

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

        String subscriberNumber = "9173849494";
        String accessToken = "_Ak28sdfl32r908sdf0q843qjlkjdf90234jlkasd98";
        payment = new Payment(subscriberNumber, accessToken) {
            public Payment setClient(HttpPostClient client) {
                this.client = client;
                return this;
            }
        }.setClient(client);
    }

    @Test
    public void chargeUser() throws ClientProtocolException,
            UnsupportedEncodingException, IOException, JSONException,
            GlobeApiException, ParameterRequiredException {
        String referenceCode = "REF-12345";
        ChargeUserResponse response = payment.charge("1", referenceCode);
        assertEquals(201, response.getResponseCode());
        assertEquals("Created", response.getResponseMessage());
        assertEquals("1", response.getAmount().toString());
    }

    private HttpResponse mockHttpResponse()
            throws UnsupportedEncodingException, JSONException {
        HttpResponseFactory factory = new DefaultHttpResponseFactory();
        HttpResponse response = factory.newHttpResponse(new BasicStatusLine(
                HttpVersion.HTTP_1_1, 201, "Created"), null);
        response.setHeader("Content-Type", "application/json; charset=utf-8");

        JSONObject responseObject = new JSONObject();

        responseObject.put("success", "true");
        responseObject.put("endUserId", "9173849494");
        responseObject.put("amount", "1");
        responseObject.put("referenceCode", "REF-12345");
        responseObject.put("access_token",
                "_Ak28sdfl32r908sdf0q843qjlkjdf90234jlkasd98");

        StringEntity stringEntity = new StringEntity(responseObject.toString());
        stringEntity.setContentType("application/json");

        response.setEntity(stringEntity);

        return response;
    }

}
