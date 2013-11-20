package ph.com.globelabs.api.util;

import java.net.URISyntaxException;
import java.util.Map;
import java.util.Set;

import org.apache.http.client.utils.URIBuilder;

public class UriBuilder {

    protected UriBuilder() {

    }

    /**
     * Builds a URI and returns it in a string format.
     * 
     * @param requestUri
     *            Existing URI with protocol, host, and get parameters (if any).
     * @param parameters
     *            A Map of get parameters. The parameter name is the key.
     * @return URI with added get parameters.
     * @throws URISyntaxException
     */
    public static String buildToString(String requestUri,
            Map<String, String> parameters) throws URISyntaxException {
        URIBuilder builder = new URIBuilder(requestUri);

        if (parameters != null) {
            Set<String> keys = parameters.keySet();
            for (String key : keys) {
                builder.setParameter(key, parameters.get(key));
            }
        }

        return builder.toString();
    }

}
