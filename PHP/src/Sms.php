<?php
class Sms extends GlobeApi
{
    //PUBLIC VARIABLES
    public $version;
    public $recepient;
    public $message;
    public $description;
    public $address;
    public $senderName;
    public $clientCorrelator;
    public $criteria;
    public $subscriptionId;
    public $accessToken;

    const CURL_URL = 'http://%s/smsmessaging/%s/outbound/%s/';

    /**
     * creates an sms
     *
     * @param string|null   $version        the api version to be used
     * @param string|null   $address        the shortcode
     */
    public function __construct(
        $address = null,
        $version = null
    ) {
        $this->version = $version;
        $this->address = $address;
    }

    /**
     * Parses the request and returns it as an array
     * 
     * @param  string|array   the request parameter
     * @return array
     */
    public function getMessage($request)
    {
        return (is_null($request)) ? array() : (is_array($request)) ? $request : json_decode($request);
    }

    /**
     * triggers the send or the message
     * 
     * @param  string|null  $accesstoken        the access token of the user to be charged
     * @param  string|null  $number             the number of the user to be charged
     * @param  string|null  $message            the message to be sent
     * @param  boolean $bodyOnly                returns the header if set to false
     * @return array
     */
    public function sendMessage(
        $accesstoken = null,
        $number = null,
        $message = null,
        $bodyOnly = true
    ) {
        if($accesstoken) {
            $this->accessToken = $accesstoken;
        }

        if($number) {
            $this->recepient = $number;
        }

        if($message) {
            $this->message = $message;
        }

        if(!$this->recepient) {
            throw new Exception('recepient is required');
        }

        if(!$this->message) {
            throw new Exception('message is required');
        }

        if(!$this->address) {
            throw new Exception('shortcode is required');
        }

        $url = sprintf(
            Sms::CURL_URL,
            GlobeAPI::API_ENDPOINT,
            $this->version,
            urlencode($this->address)
        );

        $url.='requests';
        $format = "";

        $postFields = array(
            'access_token' => $this->accessToken,
            'address' => $this->recepient,
            'message' => $this->message,
            'senderAddress' => $this->address,
            'description' => $this->description,
            'clientCorrelator' => $this->clientCorrelator
        );

        $postFields = array_filter($postFields);

        $response = $this->_curlPost($url, $postFields);

        return $this->getReturn($response, $bodyOnly);
    }
}