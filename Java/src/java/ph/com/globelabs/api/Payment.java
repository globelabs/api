package ph.com.globelabs.api;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.json.JSONException;

import ph.com.globelabs.api.exception.GlobeApiException;
import ph.com.globelabs.api.exception.ParameterRequiredException;
import ph.com.globelabs.api.request.HttpPostClient;
import ph.com.globelabs.api.response.ChargeUserResponse;
import ph.com.globelabs.api.util.UriBuilder;

public class Payment {

    private final static String CHARGE_USER_URI = "http://devapi.globelabs.com.ph/payment/{version}/transactions/amount";

    protected HttpPostClient client;

    protected String version = "v1";

    protected String subscriberNumber;
    protected String accessToken;

    /**
     * 
     * @param subscriberNumber
     *            The ten digit subscriber number with format 9xxxxxxxxx.
     *            Subscriber number and access token must match.
     * @param accessToken
     *            Access token for the given subscriber. Subscriber number and
     *            access token must match.
     * @throws GlobeApiException
     */
    public Payment(String subscriberNumber, String accessToken)
            throws GlobeApiException {
        super();

        this.subscriberNumber = subscriberNumber;
        this.accessToken = accessToken;

        try {
            client = new HttpPostClient();
        } catch (UnsupportedEncodingException e) {
            throw new GlobeApiException(e.getMessage(), e);
        }
    }

    /**
     * Charges a subscriber who has already completed the authorization process.
     * 
     * @param amount
     *            Currency format. Must be only up to two decimal places.
     * @param referenceCode
     *            Unique reference code to identify the transaction.
     * @return See {@link ChargeUserResponse}
     * @throws GlobeApiException
     * @throws ParameterRequiredException
     */
    public ChargeUserResponse charge(BigDecimal amount, String referenceCode)
            throws GlobeApiException, ParameterRequiredException {
        try {
            validateParameters(amount, referenceCode);

            amount = amount.setScale(2, RoundingMode.CEILING);
            
            Map<String, String> parameters = new HashMap<String, String>();
            parameters.put("endUserId", subscriberNumber);
            parameters.put("amount", amount.toString());
            parameters.put("referenceCode", referenceCode);
            client.setJsonStringEntity(parameters);
            HttpResponse response = client.execute(getRequestURI());

            String contentType = response.getEntity().getContentType()
                    .getValue();
            String[] contentTypes = contentType.split(";");
            contentType = contentTypes[0];
            if ("application/json".equals(contentType)) {
                return new ChargeUserResponse(response);
            } else {
                return new ChargeUserResponse(response.getStatusLine()
                        .getStatusCode(), response.getStatusLine()
                        .getReasonPhrase());
            }
        } catch (JSONException e) {
            throw new GlobeApiException(e.getMessage(), e);
        } catch (IOException e) {
            throw new GlobeApiException(e.getMessage(), e);
        } catch (URISyntaxException e) {
            throw new GlobeApiException(e.getMessage(), e);
        }
    }

    private void validateParameters(BigDecimal amount, String referenceCode)
            throws ParameterRequiredException {
        if (isValid(amount, referenceCode)) {
            String exceptionMessage = "";
            if (referenceCode == null) {
                exceptionMessage += "Reference code must not be null. ";
            }
            if (amount == null) {
                exceptionMessage += "Amount must not be null. ";
            } else if (amount.compareTo(BigDecimal.ZERO) == -1) {
                exceptionMessage += "Amount must not be negative. ";
            }
            if (subscriberNumber == null || accessToken == null) {
                exceptionMessage += "Subscriber number and access token must not be null. ";
            }
            throw new ParameterRequiredException(exceptionMessage);
        }
    }

    private boolean isValid(BigDecimal amount, String referenceCode) {
        return referenceCode == null || amount == null
                || amount.compareTo(BigDecimal.ZERO) == -1
                || subscriberNumber == null || accessToken == null;
    }

    private String getRequestURI() throws URISyntaxException,
            UnsupportedEncodingException {
        Map<String, String> parameters = new HashMap<String, String>();

        String uri = CHARGE_USER_URI;
        uri = uri
                .replace("{version}", URLEncoder.encode(version, "ISO-8859-1"));

        parameters.put("access_token", accessToken);

        return UriBuilder.buildToString(uri, parameters);
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getSubscriberNumber() {
        return subscriberNumber;
    }

    public void setSubscriberNumber(String subscriberNumber) {
        this.subscriberNumber = subscriberNumber;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    @Override
    public String toString() {
        return "Payment [client=" + client + ", version=" + version
                + ", subscriberNumber=" + subscriberNumber + ", accessToken="
                + accessToken + "]";
    }

}
