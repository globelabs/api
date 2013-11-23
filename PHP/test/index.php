<?php
    session_start();
    require ('api/PHP/src/GlobeApi.php');
    $globe = new GlobeApi();
    $auth = $globe->auth(
        [KEY],
        [SECRET]
    );
    
    if(!isset($_SESSION['code'])) {
        $loginUrl = $auth->getLoginUrl();
        header('Location: '.$loginUrl); 
        exit;
    }
    
    if(!isset($_SESSION['access_token'])) {
        $response = $auth->getAccessToken($_SESSION['code']);
        $_SESSION['access_token'] = $response['access_token'];
        $_SESSION['subscriber_number'] = $response['subscriber_number'];
    }

    $sms = $globe->sms(5286);
    $response = $sms->sendMessage($_SESSION['access_token'], $_SESSION['subscriber_number'], 'rakers api');

    $charge = $globe->payment(
        $_SESSION['access_token'],
        $_SESSION['subscriber_number']
    );

    $response = $charge->charge(
        0,
        '52861000001'
    );
?>
