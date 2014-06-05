package ph.com.globelabs.api;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.json.JSONException;

import ph.com.globelabs.api.exception.GlobeApiException;
import ph.com.globelabs.api.exception.ParameterRequiredException;
import ph.com.globelabs.api.request.HttpPostClient;
import ph.com.globelabs.api.response.InboundSmsMessage;
import ph.com.globelabs.api.response.SendSmsResponse;
import ph.com.globelabs.api.response.SmsResponse;
import ph.com.globelabs.api.util.UriBuilder;

public class Sms {

    private final static String SEND_SMS_URI = "http://devapi.globelabs.com.ph/smsmessaging/{version}/outbound/{shortCode}/requests";

    protected HttpPostClient client;

    protected String version = "v1";
    protected String shortCode;

    /**
     * 
     * @param shortCode
     *            The last 4 digits of your app short code (2158XXXX).
     * @throws GlobeApiException
     */
    public Sms(String shortCode) throws GlobeApiException {
        super();

        this.shortCode = shortCode;

        try {
            client = new HttpPostClient();
        } catch (UnsupportedEncodingException e) {
            throw new GlobeApiException(e.getMessage(), e);
        }
    }

    /**
     * Sends an SMS to a subscriber who has already completed the authorization
     * process.
     * 
     * @param subscriberNumber
     *            The ten digit subscriber number with format 9xxxxxxxxx.
     *            Subscriber number and access token must match.
     * @param accessToken
     *            Access token for the given subscriber. Subscriber number and
     *            access token must match.
     * @param message
     *            Message must be 160 characters or less.
     * @return See {@link SendSmsResponse}
     * @throws ParameterRequiredException
     * @throws GlobeApiException
     */
    public SendSmsResponse sendMessage(String subscriberNumber,
            String accessToken, String message)
            throws ParameterRequiredException, GlobeApiException {
        try {
            validateParameters(subscriberNumber, accessToken, message);

            Map<String, String> parameters = new HashMap<String, String>();
            parameters.put("address", subscriberNumber);
            parameters.put("message", message);
            client.setJsonStringEntity(parameters);
            HttpResponse response = client.execute(getRequestURI(accessToken));

            String contentType = response.getEntity().getContentType()
                    .getValue();
            String[] contentTypes = contentType.split(";");
            contentType = contentTypes[0];
            if ("application/json".equals(contentType)) {
                return new SendSmsResponse(response);
            } else {
                return new SendSmsResponse(response.getStatusLine()
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

    /**
     * Sends an SMS to a subscriber who has already completed the authorization
     * process.
     * 
     * @param subscriberNumber
     *            The ten digit subscriber number with format 9xxxxxxxxx.
     *            Subscriber number and access token must match.
     * @param accessToken
     *            Access token for the given subscriber. Subscriber number and
     *            access token must match.
     * @param message
     *            Message must be 160 characters or less.
     * @param clientCorrelator
     *            uniquely identifies this create SMS request. If there is a
     *            communication failure during the request, using the same
     *            clientCorrelator when retrying the request allows the operator
     *            to avoid sending the same SMS twice.
     * @return See {@link SendSmsResponse}
     * @throws ParameterRequiredException
     * @throws GlobeApiException
     */
    public SendSmsResponse sendMessage(String subscriberNumber,
            String accessToken, String message, String clientCorrelator)
            throws ParameterRequiredException, GlobeApiException {
        try {
            validateParameters(subscriberNumber, accessToken, message);

            Map<String, String> parameters = new HashMap<String, String>();
            parameters.put("address", subscriberNumber);
            parameters.put("message", message);
            parameters.put("clientCorrelator", clientCorrelator);
            client.setJsonStringEntity(parameters);
            HttpResponse response = client.execute(getRequestURI(accessToken));

            String contentType = response.getEntity().getContentType()
                    .getValue();
            String[] contentTypes = contentType.split(";");
            contentType = contentTypes[0];
            if ("application/json".equals(contentType)) {
                return new SendSmsResponse(response);
            } else {
                return new SendSmsResponse(response.getStatusLine()
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

    private void validateParameters(String subscriberNumber,
            String accessToken, String message)
            throws ParameterRequiredException {
        if (isValid(subscriberNumber, accessToken, message)) {
            String exceptionMessage = "";
            if (message == null) {
                exceptionMessage += "Message must not be null. ";
            } else if (message.length() > 160) {
                exceptionMessage += "Message must not exceed 160 characters. ";
            }
            if (subscriberNumber == null || accessToken == null) {
                exceptionMessage += "Subscriber number and access token must not be null. ";
            }
            throw new ParameterRequiredException(exceptionMessage);
        }
    }

    private boolean isValid(String subscriberNumber, String accessToken,
            String message) {
        return message == null || message.length() > 160
                || subscriberNumber == null || accessToken == null;
    }

    private String getRequestURI(String accessToken) throws URISyntaxException,
            UnsupportedEncodingException {
        Map<String, String> parameters = new HashMap<String, String>();

        String uri = SEND_SMS_URI;
        uri = uri
                .replace("{version}", URLEncoder.encode(version, "ISO-8859-1"));
        uri = uri.replace("{shortCode}",
                URLEncoder.encode(shortCode, "ISO-8859-1"));

        parameters.put("access_token", accessToken);

        return UriBuilder.buildToString(uri, parameters);
    }

    /**
     * Parses a raw body sent by the system to the configured notifyURL (in the
     * Globe Labs developer site) into an SMS response.
     * 
     * @param rawBody
     *            The content sent by the system to the notifyURL of content
     *            type "application/json" and charset UTF-8. <br />
     *            Parameters must consist of the following: <br />
     *            inboundSMSMessageList, numberOfMessagesInThisBatch,
     *            resourceURL, totalNumberOfPendingMessages, and
     *            inboundSMSMessage (array which consists of dateTime,
     *            destinationAddress, messageId, message, resourceURL, and
     *            senderAddress.
     * @return SMS Response object which includes a list of messages of type
     *         {@link InboundSmsMessage}, number of messages in the batch, total
     *         number of pending messages, and resourceURL.
     * 
     * @throws GlobeApiException
     */
    public SmsResponse getMessage(String rawBody) throws GlobeApiException {
        try {
            SmsResponse response = new SmsResponse(rawBody);
            return response;
        } catch (JSONException e) {
            throw new GlobeApiException("Raw body cannot be parsed.");
        }
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getShortCode() {
        return shortCode;
    }

    public void setShortCode(String shortCode) {
        this.shortCode = shortCode;
    }

    @Override
    public String toString() {
        return "Sms [client=" + client + ", version=" + version
                + ", shortCode=" + shortCode + "]";
    }

}
