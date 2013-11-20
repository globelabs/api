package ph.com.globelabs.api;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.utils.URIBuilder;
import org.json.JSONException;

import ph.com.globelabs.api.exception.GlobeApiException;
import ph.com.globelabs.api.request.HttpPostClient;
import ph.com.globelabs.api.response.AccessTokenResponse;
import ph.com.globelabs.api.util.UriBuilder;

public class OAuth {

    private final static String REQUEST_URI = "http://developer.globelabs.com.ph/dialog/oauth";
    private final static String ACCESS_URI = "http://developer.globelabs.com.ph/oauth/access_token";

    protected HttpPostClient client;

    public OAuth() throws GlobeApiException {
        super();
        try {
            client = new HttpPostClient();
        } catch (UnsupportedEncodingException e) {
            throw new GlobeApiException(e.getMessage(), e);
        }
    }

    /**
     * Builds a login URL from a given app ID. This URL is used for the OAuth
     * first leg.
     * 
     * @param appId
     *            Given app ID by Globe Labs
     * @return Login URL for user subscription and authentication to the app
     * @throws GlobeApiException
     */
    public String getLoginUrl(String appId) throws GlobeApiException {
        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put("app_id", appId);

        try {
            String loginUrl = UriBuilder.buildToString(REQUEST_URI, parameters);
            return loginUrl;
        } catch (URISyntaxException e) {
            throw new GlobeApiException(
                    "Given appId is invalid. Login URL cannot be parsed.", e);
        }
    }

    /**
     * Retrieves the access token for a given subscriber provided the subscriber
     * has completed the authentication process through web (via the login URL).
     * 
     * @param appId
     *            Given app ID by Globe Labs
     * @param appSecret
     *            Given app secret by Globe Labs
     * @param code
     *            The code sent by Globe Labs to the callback URL after
     *            subscriber has completed the web authentication process
     * @return Access token and subscriber number
     * @throws GlobeApiException
     */
    public AccessTokenResponse getAccessToken(String appId, String appSecret,
            String code) throws GlobeApiException {
        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put("app_id", appId);
        parameters.put("app_secret", appSecret);
        parameters.put("code", code);

        try {
            String requestUri = UriBuilder
                    .buildToString(ACCESS_URI, parameters);
            HttpResponse response = client.execute(requestUri);

            String contentType = response.getEntity().getContentType()
                    .getValue();
            String[] contentTypes = contentType.split(";");
            contentType = contentTypes[0];
            if ("application/json".equals(contentType)) {
                return new AccessTokenResponse(response);
            } else {
                return new AccessTokenResponse(response.getStatusLine()
                        .getStatusCode(), response.getStatusLine()
                        .getReasonPhrase());
            }
        } catch (ClientProtocolException e) {
            throw new GlobeApiException(e.getMessage(), e);
        } catch (URISyntaxException e) {
            throw new GlobeApiException(e.getMessage(), e);
        } catch (IOException e) {
            throw new GlobeApiException(e.getMessage(), e);
        } catch (IllegalStateException e) {
            throw new GlobeApiException(e.getMessage(), e);
        } catch (JSONException e) {
            throw new GlobeApiException(e.getMessage(), e);
        }
    }

    /**
     * Parses the access token for a given subscriber provided the subscriber
     * has completed the authentication process via SMS.
     * 
     * @param requestURL
     *            The callback URL along with the query parameters as called by
     *            the system upon SMS subscription by the user. Query parameters
     *            must include access_token and subscription_number.
     * @return Access token and subscriber number
     * @throws GlobeApiException
     */
    public AccessTokenResponse getAccessToken(String requestURL)
            throws GlobeApiException {
        try {
            URIBuilder builder = new URIBuilder(requestURL);
            List<NameValuePair> queryParams = builder.getQueryParams();

            String accessToken = null;
            String subscriberNumber = null;

            for (NameValuePair pair : queryParams) {
                if ("access_token".equals(pair.getName())) {
                    accessToken = pair.getValue();
                } else if ("subscriber_number".equals(pair.getName())) {
                    subscriberNumber = pair.getValue();
                }
            }

            if (accessToken == null || subscriberNumber == null) {
                throw new GlobeApiException("Request URL cannot be parsed");
            } else {
                return new AccessTokenResponse(accessToken, subscriberNumber);
            }
        } catch (URISyntaxException e) {
            throw new GlobeApiException("Request URL cannot be parsed", e);
        }
    }

    public HttpPostClient getClient() {
        return client;
    }

}
