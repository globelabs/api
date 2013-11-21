package ph.com.globelabs.api.response;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

import org.apache.http.HttpResponse;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * This object is created from the expected response of the server. Obtainable
 * information include the following: success, amount, subscriberNumber,
 * referenceCode, accessToken, and error (if any).
 * 
 * This response also has a responseCode, responseMessage, and holds the raw
 * HttpResponse. See {@link Response}.
 * 
 */
public class ChargeUserResponse extends Response {

    private boolean success;
    private BigDecimal amount;
    private String subscriberNumber;
    private String referenceCode;
    private String accessToken;

    private String error;

    public ChargeUserResponse(HttpResponse httpResponse)
            throws IllegalStateException, JSONException, IOException {
        super(httpResponse);

        JSONObject responseContent = new JSONObject(super.getContent());

        if (responseContent.has("success")) {
            this.amount = new BigDecimal(responseContent.getString("amount"))
                    .setScale(2, RoundingMode.CEILING);
            this.success = responseContent.getBoolean("success");
            this.subscriberNumber = responseContent.getString("endUserId");
            this.referenceCode = responseContent.getString("referenceCode");
            this.accessToken = responseContent.getString("access_token");
        }

        if (responseContent.has("error")) {
            this.error = (responseContent.getString("error") != null) ? responseContent
                    .getString("error") : "Subscriber may be out of balance";
        }
    }

    public ChargeUserResponse(int statusCode, String reasonPhrase) {
        super(statusCode, reasonPhrase);
    }

    public boolean isSuccess() {
        return success;
    }

    public String getSubscriberNumber() {
        return subscriberNumber;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getReferenceCode() {
        return referenceCode;
    }

    public String getError() {
        return error;
    }

    @Override
    public String toString() {
        if (success == true) {
            return "ChargeUserResponse [success=" + success + ", amount="
                    + amount.toString() + ", subscriberNumber="
                    + subscriberNumber + ", referenceCode=" + referenceCode
                    + ", accessToken=" + accessToken + "] " + super.toString();
        } else {
            return "ChargeUserResponse [error=" + error + "] "
                    + super.toString();
        }
    }

}
