<?php
    include('PHP/GlobeApi.php');

    session_start();

    $globe = new GlobeApi('v1');

    $oAuth = $globe->oAuth(
        '47jbeudg4MkfjgiBaaT4oyfExjzkuRjB',
        '92fbf46bc42ddb6a768681b8f18e95454f2ba7d9bfea877ec04ab4e2f96723a3'
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
    // $response = $sms->send($_SESSION['access_token']);
    // echo 'send response: ';print_r($response);echo '<br/>';

    // $charge = $globe->payment(
    //     '29268292535',
    //     '100',
    //     '1'
    // );

    // $response = $charge->charge($_SESSION['access_token']);
    // echo 'charge response: ';print_r($response);echo '<br/>';

?>