package ph.com.globelabs.api.response;

import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.StatusLine;

/**
 * This class holds a response code and response message which represents the
 * HTTP status code and reason phrase of the {@link HttpResponse} it was created
 * from. This class also holds said {@link HttpResponse}.
 * 
 */
public class Response {

    private int responseCode;
    private String responseMessage;

    private StatusLine statusLine;
    private String content;

    public Response(HttpResponse httpResponse) throws ParseException,
            IOException {
        this.responseCode = httpResponse.getStatusLine().getStatusCode();
        this.responseMessage = httpResponse.getStatusLine().getReasonPhrase();

        this.statusLine = httpResponse.getStatusLine();
        this.content = IOUtils.toString(httpResponse.getEntity().getContent());
    }

    public Response(int statusCode, String reasonPhrase) {
        this.responseCode = statusCode;
        this.responseMessage = reasonPhrase;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public StatusLine getStatusLine() {
        return statusLine;
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return "Response [responseCode=" + responseCode + ", responseMessage="
                + responseMessage + ", statusLine=" + statusLine + ", content="
                + content + "]";
    }

}
