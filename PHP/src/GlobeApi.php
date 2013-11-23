<?php
require_once(dirname(__FILE__).'/Sms.php');
require_once(dirname(__FILE__).'/Auth.php');
require_once(dirname(__FILE__).'/Payment.php');

class GlobeApi {
    //THE BASE URL OF THE API
    const API_ENDPOINT = 'devapi.globelabs.com.ph';
    const AUTH_POINT = 'developer.globelabs.com.ph';

    //protected variables
    protected $version;
    protected $shortCode;

    /**
     * constructor of the Wrapper Class
     * 
     * @param string|null $version   the version to be used
     */
    public function __construct($version = 'v1') {
        $this->version = $version;
    }

    /**
     * Used to instantiate an sms object to be sent
     * 
     * @param  string|number    $shortCode
     * @param  string|null      $version        included for extensibility
     * @return [type]
     */
    public function sms($shortCode, $version = null)
    {
        //check if the user passed a version parameter and use if any
        $ver = $version ? $version : $this->version;

        //use the set shortcode if any
        if($shortCode) {
            $this->shortCode = $shortCode;
        }

        return new Sms($this->shortCode, $ver);
    }

    /**
     * used to instantiate a payment object to be charged to a user
     * 
     * @param  string|null      $accessToken        the access token of the user to be charged
     * @param  string|null      $endUserId          the number of the user to be charged
     * @param  string|null      $version            included fo extensibility
     * @return Payment
     */
    public function payment( 
        $accessToken = null,
        $endUserId = null,
        $version = null
    ) {
        //check if the user passed a version parameter and use if any
        $ver = $version ? $version : $this->version;
        return new Payment(
            $ver,
            $endUserId,
            $accessToken
        );
    }

    /**
     * used to instantiate an Auth class
     * @param  string   $apikey         the api key of the app
     * @param  string   $apisecret      the api secret of the app
     * @return Auth
     */
    public function auth($apikey, $apisecret)
    {
        return new Auth($apikey, $apisecret);
    }

    public function __call($name, $arguments)
    {
        $prefix = strtolower(substr($name, 0, 3));
        $property = $this->lcfirst(substr($name, 3));

        if ($prefix === 'set') {
            if (!property_exists($this, $property)) {
                throw new Exception($property.' doesnt exist');
            }

            if(!is_string($arguments[0])) {
                throw new Exception($name.' expects a string paramerter');
            }

            $value = $arguments[0];
            $this->$property = $value;
        }

        return $this;
    }

    protected function _curlDelete($url) {
        $ch = curl_init();
        curl_setopt($ch, CURLOPT_HEADER, true);
        curl_setopt($ch, CURLOPT_VERBOSE, 1);
        curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
        curl_setopt($ch, CURLOPT_URL,$url);
        curl_setopt($ch, CURLOPT_HTTPHEADER, array(
            'Accept: application/json',
            'Content-Type: application/json'
        ));
        curl_setopt($ch, CURLOPT_CUSTOMREQUEST, 'DELETE');

        $response = curl_exec($ch);
        $header_size = curl_getinfo($ch, CURLINFO_HEADER_SIZE);
        list($header, $body) = explode("\r\n\r\n", $response, 2);

        curl_close($ch);

        return array('header' => $header, 'body' => json_decode($body, true));
    }

    protected function _curlGet($url, $fields) {
        $fields_string = '';
        foreach($fields as $key => $value) {
            $fields_string .= $key.'='.urlencode($value).'&';
        }

        rtrim($fields_string,'&');

        $ch = curl_init($url.'?'.$fields_string);
        curl_setopt($ch, CURLOPT_HEADER, true);
        curl_setopt($ch, CURLOPT_VERBOSE, 1);
        curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
        curl_setopt($ch, CURLOPT_HTTPHEADER, array(
            'Accept: application/json'
        ));

        $response = curl_exec($ch);

        $header_size = curl_getinfo($ch, CURLINFO_HEADER_SIZE);
        list($header, $body) = explode("\r\n\r\n", $response, 2);

        curl_close($ch);

        return array('header' => $header, 'body' => json_decode($body, true));
    }

    protected function _curlPost($url, $fields = array()) {
        $data = json_encode($fields);

        $ch = curl_init($url);
        curl_setopt($ch, CURLOPT_HEADER, true);
        curl_setopt($ch, CURLOPT_VERBOSE, 1);
        curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
        curl_setopt($ch, CURLOPT_HTTPHEADER, array(
            'Accept: application/json',
            'Content-Type: application/json',
            'Content-Length: ' . strlen($data))
        );
        curl_setopt($ch, CURLOPT_POST, true);
        curl_setopt($ch, CURLOPT_POSTFIELDS, $data);

        $response = curl_exec($ch);
        $header_size = curl_getinfo($ch, CURLINFO_HEADER_SIZE);

        $temp = explode("\r\n\r\n", $response, 2);
        curl_close($ch);

        $header = $temp[0];
        $body = $temp[1];

        return array('header' => $header, 'body' => json_decode($body, true));
    }

    protected function getReturn($response, $bodyOnly) {
        if($bodyOnly) {
            return $response['body'];
        }

        return $response;
    }

    protected function lcfirst($str) {
        $str[0] = strtolower($str[0]);
        return $str;
    }
}