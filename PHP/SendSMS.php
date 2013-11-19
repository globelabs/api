<?php
    class SendSMS extends GlobeAPI
    {
        //PUBLIC VARIABLES
        public $apiVersion;
        public $recepient;
        public $message;
        public $address;
        public $clientCorrelator;
        public $criteria;
        public $subscriptionId;
        public $accessToken;

        const CURL_URL = 'http://%s/smsmessaging/%s/outbound/%s/';

        /**
         * Constructor for the SendSMS
         *
         * @param string|null   $apiVersion         the api version to be used
         * @param string|null   $recepient          the recepient's number
         * @param string|null   $message            the message to be sent
         * @param string|null   $address            the shortcode
         * @param string|null   $accessToken        the access token
         * @param string|null   $clientCorrelator   the client correlator
         */
        public function __construct(
            $apiVersion = null,
            $recepient = null,
            $message = null,
            $address = null,
            $clientCorrelator = null
        ) {
            $this->apiVersion = $apiVersion;
            $this->recepient = $recepient;
            $this->message = $message;
            $this->address = $address;
            $this->clientCorrelator = $clientCorrelator;
        }


        /**
         * Triggers the send of the sms
         *
         * @param  boolean|null $bodyOnly tells the wrapper to send the headers if set to false
         * @return array
         */
        public function send($accesstoken, $bodyOnly = true)
        {

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
                SendSMS::CURL_URL,
                GlobeAPI::API_ENDPOINT,
                $this->apiVersion,
                urlencode($this->address)
            );

            $url.='requests';
            $format = "";

            $postFields = array(
                'access_token' => $accesstoken,
                'address' => $this->recepient,
                'message' => $this->message,
                'senderAddress' => $this->address,
                'clientCorrelator' => $this->clientCorrelator
            );

            $postFields = array_filter($postFields);

            $response = $this->_curlPost($url, $postFields);

            return $this->getReturn($response, $bodyOnly);
        }
    }
?>