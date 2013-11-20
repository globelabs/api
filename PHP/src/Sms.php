<?php
    class Sms extends GlobeApi
    {
        //PUBLIC VARIABLES
        public $version;
        public $recepient;
        public $message;
        public $address;
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
         * This is not yet implemented in the API
         * @param  [type] $registrationID [description]
         * @param  [type] $maxBatchSize   [description]
         * @return [type]                 [description]
         */
        public function receiveSMS(
            $registrationID = null,
            $maxBatchSize = null
        ) {
            $this->ReceiveSMS = new ReceiveSMS(
                $this->version,
                $registrationID,
                $maxBatchSize
            );

            return $this->ReceiveSMS;
        }

        /**
         * Triggers the send of the sms
         *
         * @param  boolean|null $bodyOnly tells the wrapper to send the headers if set to false
         * @return array
         */
        public function send(
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
                'clientCorrelator' => $this->clientCorrelator
            );

            $postFields = array_filter($postFields);

            $response = $this->_curlPost($url, $postFields);

            return $this->getReturn($response, $bodyOnly);
        }
    }
?>