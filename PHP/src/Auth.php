<?php
class Auth extends GlobeApi
{
    const LOGIN_URL = 'http://%s/dialog/oauth';
    const AUTH_URL = 'http://%s/oauth/access_token';

    private $api_key;
    private $api_secret;

    /**
     * Constructor of Auth
     *
     * @param string $api_key    the api key of the app
     * @param string $api_secret the api secret of the app
     */
    public function __construct(
        $api_key,
        $api_secret
    ) {
        $this->api_key = $api_key;
        $this->api_secret = $api_secret;
    }
    
    /**
     * Parses the request and returns the code
     * 
     * @param  string|array   the request parameter
     * @return array
     */
    public function getCode($request)
    {
        return (isset($request['code'])) ? $request['code'] : NULL;
    }

    /**
     * generates the login/auth url\
     *
     * @return string
     */
    public function getLoginUrl()
    {
        return sprintf(Auth::LOGIN_URL, GlobeApi::AUTH_POINT).'?app_id='.$this->api_key;
    }

    /**
     * Grabs the access token given the code
     *
     * @param  string       $code     access code grabbed from the callback
     * @param  boolean|null $bodyOnly returns the headers if set to false
     * @return array
     */
    public function getAccessToken($code, $sms = false, $bodyOnly = true)
    {
        if($sms && $code['access_token']) {
            return $code;
        } else if($sms) {
            return NULL;
        }

        $url = sprintf(Auth::AUTH_URL, GlobeApi::AUTH_POINT);

        $fields = array(
            'app_id' => $this->api_key,
            'app_secret' => $this->api_secret,
            'code' => $code
        );

        $url.='?'.http_build_query($fields);

        $response = $this->_curlPost($url);

        return $this->getReturn($response, $bodyOnly);
    }
}