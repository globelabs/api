<?php
    class ReceiveSMS extends GlobeAPI
    {
        public $apiVersion;
        public $registrationId;
        public $maxBatchSize;
        public $destinationAddress;
        public $notifyUrl;
        public $criteria;
        public $notificationFormat;
        public $clientCorrelator;
        public $callbackData;
        public $subscriptionId;

        public $curlURL = 'http://%s/%s/smsmessaging/inbound/';

        public function __construct(
            $apiVersion = null,
            $registrationId = null,
            $maxBatchSize = null
        ) {

            $this->registrationId = $registrationId;
            $this->maxBatchSize = $maxBatchSize;
            $this->apiVersion = $apiVersion;
        }

        public function receive(
            $registrationId = null,
            $maxBatchSize = null,
            $bodyOnly = true
        ) {
            if(!$registrationId && !$this->registrationId) {
                throw new Exception('receive expects a registrationId.
                    set as parameter or use setRegistrationId');
            }

            if($maxBatchSize) {
                $this->maxBatchSize = $maxBatchSize;
            }

            if(!is_integer($this->maxBatchSize)) {
                throw new Exception('maxBatchSize should be an integer');
            }

            $url = sprintf($this->curlURL, GlobeAPI::API_ENDPOINT, $this->apiVersion);
            $url.='registration/'.$this->registrationId.'/messages';

            $fields = array('maxBatchSize' => $this->maxBatchSize);

            $fields = array_filter($fields);

            //$response = $this->_curlGet($url, $fields);
            $response = $this->getMockReceiveResponse();

            return $this->getReturn($response, $bodyOnly);

        }

        public function subscribe(
            $destinationAddress = null,
            $notifyUrl = null,
            $criteria = null,
            $notificationFormat = null,
            $clientCorrelator = null,
            $callbackData = null,
            $bodyOnly = true
        ) {
            $this->destinationAddress = ($destinationAddress)
                ? $destinationAddress
                : $this->destinationAddress;
            $this->notifyUrl = ($notifyUrl) ? $notifyUrl
                : $this->notifyUrl;

            if(!$this->destinationAddress
                || !is_string($this->destinationAddress)) {
                throw new Exception('subscribe expects a string
                    destinationAddress. set as parameter or
                    use setDestinationAddress');
            }

            if(!$this->notifyUrl
                || !is_string($this->notifyUrl)) {
                throw new Exception('subscribe expects a url notifyUrl. set
                    as parameter or use setNotifyUrl');
            }

            $fields = array(
                'destinationAddress' => $this->destinationAddress,
                'notifyUrl' => $this->notifyUrl,
                'criteria' => $this->criteria,
                'notificationFormat' => $this->notificationFormat,
                'clientCorrelator' => $this->clientCorrelator,
                'callbackData' => $this->callbackData
            );

            $fields = array_filter($fields);

            $url = sprintf($this->curlURL, GlobeAPI::API_ENDPOINT, $this->apiVersion);
            $url.='subscriptions/';

            //$response = $this->_curlPost($url, $fields);
            $response = $this->getMockSubscribeResponse();

            if(strpos($response['header'], 'Created')) {
                $parsed = $parsed['deliveryReceiptSubscription']['resourceURL'];
                $id = explode('/', $parsed);
                $id = $id[count($id)-1];
                $this->subscriptionId = $id;
            }

            return $this->getReturn($response, $bodyOnly);
        }

        public function unsubscribe($subscriptionId = null, $bodyOnly = true)
        {
            if(($subscriptionId && !is_string($subscriptionId))
                || ($this->subscriptionId && !is_string($this->subscriptionId))
                || (!$subscriptionId && !$this->subscriptionId)
            ) {
                throw new Exception('unsubscribe expects a subscriptionId,
                    please set as parameter or use setSubscriptionId');
            }

            $url = sprintf(
                $this->curlURL,
                GlobeAPI::API_ENDPOINT,
                $this->apiVersion
            );

            $url.='subscriptions/'.($subscriptionId
                ? $subscriptionId
                : $this->subscriptionId);

            //$response = $this->_curlDelete($url);
            $response = $this->getMockUnsubscribeResponse();

            return $this->getReturn($response, $bodyOnly);
        }

    }

?>