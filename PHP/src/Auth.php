<?php
class OAuth extends GlobeApi
{
    const LOGIN_URL = 'http://%s/dialog/oauth';
    const AUTH_URL = 'http://%s/oauth/access_token';

    private $api_key;
    private $api_secret;

    /**
     * Constructor of oauth
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
     * generates the login/auth url\
     *
     * @return string
     */
    public function getLoginUrl()
    {
        return sprintf(OAuth::LOGIN_URL, GlobeApi::AUTH_POINT).'?app_id='.$this->api_key;
    }

    /**
     * Grabs the access token given the code
     *
     * @param  string       $code     access code grabbed from the callback
     * @param  boolean|null $bodyOnly returns the headers if set to false
     * @return array
     */
    public function getAccessToken($code, $bodyOnly = true)
    {
        $url = sprintf(OAuth::AUTH_URL, GlobeApi::AUTH_POINT);

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