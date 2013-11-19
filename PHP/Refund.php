<?php
    class Refund extends GlobeAPI
    {

        public $amount;
        public $clientCorrelator;
        public $serverReferenceCode;
        public $endUserId;

        public $onBehalfOf;
        public $purchaseCategoryCode;
        public $channel;
        public $taxAmount;
        public $serviceId;
        public $productId;

        public $curlURL = 'http://%s/%s/payment/%s/
            transactions/amount';

        public function __construct(
            $endUserId = null,
            $amount  = null,
            $clientCorrelator = null,
            $serverReferenceCode = null,
        ) {
            if(
                $amount &&
                (!is_numeric($amount) && !is_float($amount))
            ) {
                throw new Exception('amount should be numeric');
            }

            $this->endUserId = $endUserId;
            $this->amount = $amount;
            $this->clientCorrelator = $clientCorrelator;
            $this->serverReferenceCode = $serverReferenceCode
        }

        public function refund()
        {
            if(!$this->endUserId) {
                throw new Exception('refund expects an endUserId.');
            }

            if(!$this->amount ) {
                throw new Exception('refund expects an amount .');
            }

            if(!$this->clientCorrelator ) {
                throw new Exception('refund expects a clientCorrelator.');
            }

            if(!$this->serverReferenceCode ) {
                throw new Exception('refund expects a serverReferenceCode .');
            }

            $url = sprintf(
                $this->curlURL,
                GlobeAPI::API_ENDPOINT,
                $this->apiVersion,
                urlencode($this->endUserId)
            );

            $fields = array(
                'transactionOperationStatus' => 'refunded',
                'amount' => $this->amount,
                'clientCorrelator' => $this->clientCorrelator,
                'serverReferenceCode' => $this->serverReferenceCode,
                'onBehalfOf' => $this->onBehalfOf,
                'purchaseCategoryCode' => $this->purchaseCategoryCode,
                'channel' => $this->channel,
                'taxAmount' => $this->taxAmount,
                'serviceID' => $this->serviceId,
                'productID' => $this->productId
            );

            $fields = array_filter($fields);

            $response = $this->_curlPost($url, $fields);

            return $this->getReturn($response, $bodyOnly);
        }

        public function setAmount($amount)
        {
            if(
                $amount &&
                (!is_numeric($amount) && !is_float($amount))
            ) {
                throw new Exception('amount should be numeric');
            }

            $this->amount = $amount;
            return $this;
        }
    }
?>