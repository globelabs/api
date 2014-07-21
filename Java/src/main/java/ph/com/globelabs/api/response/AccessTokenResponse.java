package ph.com.globelabs.api.response;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * This object is created from the expected response of the server. Obtainable
 * information include the following: accessToken, subscriberNumber, and error
 * (if any).
 * 
 * This response also has a responseCode, responseMessage, and holds the raw
 * HttpResponse. See {@link Response}.
 */
public class AccessTokenResponse extends Response {

    private String accessToken;
    private String subscriberNumber;

    private String error;

    public AccessTokenResponse(HttpResponse httpResponse)
            throws IllegalStateException, JSONException, IOException {
        super(httpResponse);

        JSONObject responseContent = new JSONObject(super.getContent());

        if (responseContent.has("access_token")
                && responseContent.has("subscriber_number")) {
            this.accessToken = responseContent.getString("access_token");
            this.subscriberNumber = responseContent
                    .getString("subscriber_number");
        }

        if (responseContent.has("error")) {
            this.error = responseContent.getString("error");
        }
    }

    public AccessTokenResponse(String accessToken, String subscriberNumber) {
        super(200, "OK");
        this.accessToken = accessToken;
        this.subscriberNumber = subscriberNumber;
    }

    public AccessTokenResponse(int statusCode, String reasonPhrase) {
        super(statusCode, reasonPhrase);
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getSubscriberNumber() {
        return subscriberNumber;
    }

    public String getError() {
        return error;
    }

    @Override
    public String toString() {
        if (error == null) {
            return "AccessTokenResponse [accessToken=" + accessToken
                    + ", subscriberNumber=" + subscriberNumber + "] "
                    + super.toString();
        } else {
            return "AccessTokenResponse [error=" + error + "] "
                    + super.toString();
        }
    }

}
