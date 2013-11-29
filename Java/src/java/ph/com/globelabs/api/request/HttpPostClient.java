package ph.com.globelabs.api.request;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONException;
import org.json.JSONObject;

import ph.com.globelabs.api.util.UriBuilder;

public class HttpPostClient {

    private HttpClient client;
    private HttpPost request;

    public HttpPostClient() throws UnsupportedEncodingException {
        HttpClientBuilder builder = HttpClientBuilder.create();
        this.client = builder.build();
        this.request = new HttpPost();

        // Default StringEntity
        this.request.setEntity(new StringEntity(""));
        this.request.addHeader("Content-Type", "application/json");
        this.request.addHeader("Accept", "application/json");
    }

    public HttpResponse execute(String requestUri)
            throws ClientProtocolException, IOException, URISyntaxException {
        Map<String, String> parameters = new HashMap<String, String>();

        this.request.setURI(new URI(UriBuilder.buildToString(requestUri,
                parameters)));
        return this.client.execute(request);
    }

    public HttpEntity getEntity() {
        return this.request.getEntity();
    }

    public void setEntity(HttpEntity entity) {
        this.request.setEntity(entity);
    }

    public void setJsonStringEntity(Map<String, String> parameters)
            throws JSONException {
        JSONObject requestContent = new JSONObject();
        Set<String> names = parameters.keySet();

        for (String name : names) {
            requestContent.put(name, parameters.get(name));
        }

        StringEntity stringEntity = new StringEntity(requestContent.toString(),
                Charset.defaultCharset());

        this.request.setEntity(stringEntity);
    }

}
