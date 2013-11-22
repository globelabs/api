package ph.com.globelabs.api.sample;

import ph.com.globelabs.api.Sms;
import ph.com.globelabs.api.exception.GlobeApiException;
import ph.com.globelabs.api.exception.ParameterRequiredException;

public class SmsRunner {

    public static void main(String[] args) throws GlobeApiException,
            ParameterRequiredException {
        String shortCode = "9999";
        Sms sms = new Sms(shortCode);

        String subscriberNumber = "9173849494";
        String accessToken = "_Ak28sdfl32r908sdf0q843qjlkjdf90234jlkasd98";
        String message = "Hello World";
        System.out.println(sms.send(subscriberNumber, accessToken, message));
    }

}
