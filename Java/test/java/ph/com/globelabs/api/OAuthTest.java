package ph.com.globelabs.api;

import static org.junit.Assert.assertEquals;

import java.io.UnsupportedEncodingException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpResponseFactory;
import org.apache.http.HttpVersion;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.DefaultHttpResponseFactory;
import org.apache.http.message.BasicStatusLine;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import ph.com.globelabs.api.OAuth;
import ph.com.globelabs.api.exception.GlobeApiException;
import ph.com.globelabs.api.request.HttpPostClient;
import ph.com.globelabs.api.response.AccessTokenResponse;

public class OAuthTest {

    private OAuth oAuth;

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

        oAuth = new OAuth() {
            public OAuth setClient(HttpPostClient client) {
                this.client = client;
                return this;
            }
        }.setClient(client);
    }

    @Test
    public void getLoginUrl() throws GlobeApiException {
        String appId = "345SDxcblesfUSDoifw3ljsdfwou35aj";

        String expectedLoginURL = "http://developer.globelabs.com.ph/dialog/oauth?app_id=345SDxcblesfUSDoifw3ljsdfwou35aj";
        String actualLoginURL = oAuth.getLoginUrl(appId);

        assertEquals(expectedLoginURL, actualLoginURL);
    }

    @Test
    public void getAccessTokenViaCode() throws GlobeApiException {
        String appId = "345SDxcblesfUSDoifw3ljsdfwou35aj";
        String appSecret = "93SDf34587SDflk345u98SDFH359875987F3489SfdfESF45897egjldkfjgW348";
        String code = "93SDf34587SDflk345u98SDFH359875987F3489SfdfESF45897egjldkfjgW348967ES3648SDFJOi3u489df346jDSFkl34598sdfD34897DGXLkj987ZFnzljfdioSDF342987OCIIzn43fszxvnZV89w2324uiDFDS458fSDCzjkxczwer82349ZSDF23798dl2k4hkh2lSDf23894jhdskjfhkjhkjhjDKJSJFH34458394578989234FDS";

        AccessTokenResponse response = oAuth.getAccessToken(appId,
                appSecret, code);
        assertEquals(200, response.getResponseCode());
        assertEquals("OK", response.getResponseMessage());
        assertEquals("_Ak28sdfl32r908sdf0q843qjlkjdf90234jlkasd98",
                response.getAccessToken());
        assertEquals("9173849494", response.getSubscriberNumber());
    }

    @Test
    public void getAccessTokenViaURL() throws GlobeApiException {
        String url = "http://www.callback-123.com/?access_token=_Ak28sdfl32r908sdf0q843qjlkjdf90234jlkasd98&subscriber_number=9173849494";

        AccessTokenResponse response = oAuth.getAccessToken(url);
        assertEquals(200, response.getResponseCode());
        assertEquals("OK", response.getResponseMessage());
        assertEquals("_Ak28sdfl32r908sdf0q843qjlkjdf90234jlkasd98",
                response.getAccessToken());
        assertEquals("9173849494", response.getSubscriberNumber());
    }

    private HttpResponse mockHttpResponse()
            throws UnsupportedEncodingException, JSONException {
        HttpResponseFactory factory = new DefaultHttpResponseFactory();
        HttpResponse response = factory.newHttpResponse(new BasicStatusLine(
                HttpVersion.HTTP_1_1, 200, "OK"), null);
        response.setHeader("Content-Type", "application/json");

        JSONObject responseObject = new JSONObject();
        responseObject.put("access_token",
                "_Ak28sdfl32r908sdf0q843qjlkjdf90234jlkasd98");
        responseObject.put("subscriber_number", "9173849494");

        StringEntity stringEntity = new StringEntity(responseObject.toString());
        stringEntity.setContentType("application/json");

        response.setEntity(stringEntity);

        return response;
    }

}
