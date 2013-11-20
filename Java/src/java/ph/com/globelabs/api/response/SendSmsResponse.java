package ph.com.globelabs.api.response;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * This object is created from the expected response of the server. Obtainable
 * information include the following: success, message, address, senderAddress,
 * accessToken, and error (if any).
 * 
 * This response also has a responseCode, responseMessage, and holds the raw
 * HttpResponse. See {@link Response}.
 * 
 */
public class SendSmsResponse extends Response {

    private boolean success;
    private String message;
    private String address;
    private String senderAddress;
    private String accessToken;

    private String error;

    public SendSmsResponse(HttpResponse httpResponse)
            throws IllegalStateException, JSONException, IOException {
        super(httpResponse);

        JSONObject responseContent = new JSONObject(super.getContent());
        if (responseContent.has("success")) {
            this.success = responseContent.getBoolean("success");
            this.message = responseContent.getString("message");
            this.address = responseContent.getString("address");
            this.senderAddress = responseContent.getString("senderAddress");
            this.accessToken = responseContent.getString("access_token");
        }

        if (responseContent.has("error")) {
            this.error = responseContent.getString("error");
        }
    }

    public SendSmsResponse(int statusCode, String reasonPhrase) {
        super(statusCode, reasonPhrase);
    }

    public boolean isSuccessful() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public String getAddress() {
        return address;
    }

    public String getSenderAddress() {
        return senderAddress;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getError() {
        return error;
    }

    @Override
    public String toString() {
        if (success == true) {
            return "SendSmsResponse [success=" + success + ", message="
                    + message + ", address=" + address + ", senderAddress="
                    + senderAddress + ", accessToken=" + accessToken + "] "
                    + super.toString();
        } else {
            return "SendSmsResponse [error=" + error + "] " + super.toString();
        }
    }

}
