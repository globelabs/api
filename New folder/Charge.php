<?php
    class Charge extends GlobeAPI
    {
        public $apiVersion;
        public $endUserId;
        public $referenceCode;
        public $transactionOperationStatus;
        public $description;
        public $currency;
        public $amount;
        public $code;
        public $clientCorrelator;
        public $onBehalfOf;
        public $purchaseCategoryCode;
        public $channel;
        public $taxAmount;
        public $serviceId;
        public $productId;

        public $curlURL = 'http://%s/payment/%s/transactions/amount';

        /**
         * COnstructor of the Charge class
         *
         * @param string|null $apiVersion    the api version to use
         * @param string|null $endUserId     the number to charge
         * @param string|null $referenceCode the reference code
         * @param string|null $amount        the amount to be charged
         */
        public function __construct(
            $apiVersion = null,
            $endUserId = null,
            $referenceCode = null,
            $amount = null
        ) {

            $this->apiVersion = $apiVersion;
            $this->endUserId = $endUserId;
            $this->referenceCode = $referenceCode;
            $this->transactionOperationStatus = 'charged';
            $this->amount = $amount;
        }

        /**
         * sets $this->amount to the amount parameter
         *
         * @param number $amount the ammount to charge
         */
        public function setAmount($amount) {
            if(!is_float($amount) || !is_integer($amount)) {
                throw new Exception('amount should be float or integer');
            }

            $this->amount = $amount;
            return $this;
        }

        /**
         * Triggers charge
         * @param  string  $accessToken     the access token to be sued
         * @param  boolean $bodyOnly        returns the headers if set to false
         * @return array
         */
        public function charge($accessToken, $bodyOnly = true) {
            if(!$this->endUserId) {
                throw new Exception('charge expects an endUserId.');
            }

            if(!$this->referenceCode) {
                throw new Exception('charge expects a referenceCode.');
            }

            if(!$this->transactionOperationStatus) {
                throw new Exception('charge expects a transactionOperationStatus.');
            }

            if(!$this->amount) {
                throw new Exception('charge expects an amount.');
            }

            $url = sprintf(
                $this->curlURL,
                GlobeAPI::API_ENDPOINT,
                $this->apiVersion
            );

            $fields = array(
                'endUserId' => $this->endUserId,
                'referenceCode' => $this->referenceCode,
                'transactionOperationStatus' => $this->transactionOperationStatus,
                'amount' => $this->amount,
                'access_token' => $accessToken
            );

            $fields = array_filter($fields);

            $response = $this->_curlPost($url, $fields);

            return $this->getReturn($response, $bodyOnly);
        }
    }
?>