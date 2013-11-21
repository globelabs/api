<?php
    include('PHP/GlobeApi.php');

    session_start();

    $globe = new GlobeApi('v1');

    $oAuth = $globe->oAuth(
        'app_key',
        'app_secret'
    );

    if(!isset($_SESSION['code']) && !isset($_SESSION['access_token'])) {
        header(
            'Location: '.
            $oAuth->getAuthUrl()
        );
    }

    if(!isset($_SESSION['access_token'])) {
        $response = $oAuth->getAccessToken($_SESSION['code']);

        $_SESSION['access_token'] = $response['access_token'];
        $_SESSION['subscriber_number'] = $response['subscriber_number'];

    }

    $sms = $globe->sms(5286);
    $outbound = $sms->setMessage('adin ang pogi mo')
        ->setRecepient($_SESSION['subscriber_number']);
    $outbound->setAccessToken($_SESSION['access_token']);

    $response = $outbound->send();
    echo 'send response: ';print_r($response);echo '<br/>';    

    $response = $outbound->send(
        $_SESSION['access_token'],
        $_SESSION['subscriber_number'],
        'this message is sent alone'
    );
    echo 'send response: ';print_r($response);echo '<br/>';    


    $charge = $globe->payment(
        $_SESSION['access_token'],
        $_SESSION['subscriber_number']
    );

    //$response = $charge->charge('0', 'asdfasdfasdfasdf123123');
    echo 'charge response: ';print_r($response);echo '<br/>';

?>
