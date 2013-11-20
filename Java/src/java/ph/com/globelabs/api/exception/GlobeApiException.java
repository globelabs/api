package ph.com.globelabs.api.exception;

public class GlobeApiException extends Exception {

    private static final long serialVersionUID = -3230489572388414359L;

    public GlobeApiException(String message, Throwable cause) {
        super(message, cause);
    }

    public GlobeApiException(String message) {
        super(message);
    }

}
