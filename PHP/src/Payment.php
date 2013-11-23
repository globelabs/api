<?php
class Payment extends GlobeAPI
{
    public $version;
    public $endUserId;
    public $referenceCode;
    public $transactionOperationStatus;
    public $description;
    public $currency;
    public $accessToken;
    public $amount;
    public $code;
    public $clientCorrelator;
    public $onBehalfOf;
    public $purchaseCategoryCode;
    public $shortCode;
    public $channel;
    public $taxAmount;
    public $serviceId;
    public $productId;

    public $curlURL = 'http://%s/payment/%s/transactions/amount';

    /**
     * COnstructor of the Charge class
     *
     * @param string|null $version          the api version to use
     * @param string|null $endUserId        the number to charge
     * @param string|null $accessToken      the access token of the user to be charged
     */
    public function __construct(
        $version = null,
        $endUserId = null,
        $accessToken = null
    ) {

        $this->version = $version;
        $this->endUserId = $endUserId;
        $this->accessToken = $accessToken;
        $this->transactionOperationStatus = 'charged';
    }

    /**
     * sets $this->amount to the amount parameter
     *
     * @param number $amount the ammount to charge
     */
    public function setAmount($amount) {
        if(!is_float($amount) && !is_integer($amount) && $amount!==0) {
            throw new Exception('amount should be float or integer');
        }

        $this->amount = sprintf('%0.2f', $amount);;
        return $this;
    }

    /**
     * Triggers charge
     * @param  string|number    $amount         the access token to be sued
     * @param  number|string     $bodyOnly      returns the headers if set to false
     * @return array 
     */
    public function charge($amount=null, $refNo=null, $bodyOnly = true) {
        if($amount!==null) {
            $this->setAmount($amount);
        }

        if($refNo) {
            $this->referenceCode = $refNo;
        }

        if(!$this->endUserId) {
            throw new Exception('charge expects an endUserId.');
        }

        if(!$this->referenceCode) {
            throw new Exception('charge expects a referenceCode.');
        }

        if($this->amount === null) {
            throw new Exception('charge expects an amount.');
        }

        $url = sprintf(
            $this->curlURL,
            GlobeAPI::API_ENDPOINT,
            $this->version
        );

        $fields = array(
            'endUserId' => $this->endUserId,
            'amount' => $this->amount,
            'description' => $this->description,
            'referenceCode' => $this->referenceCode,
            'transactionOperationStatus' => $this->transactionOperationStatus,
            'clientCorrelator' => $this->clientCorrelator,
            'access_token' => $this->accessToken
        );

        $fields = array_filter($fields);

        $response = $this->_curlPost($url, $fields);

        return $this->getReturn($response, $bodyOnly);
    }
}