package ph.com.globelabs.api.sample;

import java.math.BigDecimal;

import ph.com.globelabs.api.Payment;
import ph.com.globelabs.api.exception.GlobeApiException;
import ph.com.globelabs.api.exception.ParameterRequiredException;

public class PaymentRunner {

    public static void main(String[] args) throws GlobeApiException,
            ParameterRequiredException {
        String subscriberNumber = "9173849494";
        String accessToken = "_Ak28sdfl32r908sdf0q843qjlkjdf90234jlkasd98";
        Payment payment = new Payment(subscriberNumber, accessToken);

        BigDecimal amount = new BigDecimal("1.00");
        String referenceCode = System.currentTimeMillis() + "";
        System.out.println(payment.charge(amount, referenceCode));
    }

}
