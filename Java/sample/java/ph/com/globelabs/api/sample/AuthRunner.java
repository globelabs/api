package ph.com.globelabs.api.sample;

import ph.com.globelabs.api.Auth;
import ph.com.globelabs.api.exception.GlobeApiException;

public class AuthRunner {

    public static void main(String[] args) throws GlobeApiException {
        Auth auth = new Auth();

        String appId = "345SDxcblesfUSDoifw3ljsdfwou35aj";
        System.out.println(auth.getLoginUrl(appId));

        String appSecret = "93SDf34587SDflk345u98SDFH359875987F3489SfdfESF45897egjldkfjgW348";
        String code = "93SDf34587SDflk345u98SDFH359875987F3489SfdfESF45897egjldkfjgW348967ES3648SDFJOi3u489df346jDSFkl34598sdfD34897DGXLkj987ZFnzljfdioSDF342987OCIIzn43fszxvnZV89w2324uiDFDS458fSDCzjkxczwer82349ZSDF23798dl2k4hkh2lSDf23894jhdskjfhkjhkjhjDKJSJFH34458394578989234FDS";
        System.out.println(auth.getAccessToken(appId, appSecret, code));

        String requestURL = "http://www.callback-123.com/?access_token=_Ak28sdfl32r908sdf0q843qjlkjdf90234jlkasd98&subscriber_number=9173849494";
        System.out.println(auth.getAccessToken(requestURL));
    }

}
