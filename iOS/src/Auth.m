//
//  Auth.m
//  GL Wrapper IOS
//
//  Created by SuperDan on 11/28/13.
//  Copyright (c) 2013 SuperDan. All rights reserved.
//

#import "Auth.h"
#import "GlobeApi.h"

@implementation Auth: GlobeApi

NSString *OAUTH_URL = @"http://developer.globelabs.com.ph/dialog/oauth";
NSString *ACCESS_TOKEN_URL = @"http://developer.globelabs.com.ph/oauth/access_token";

//set the App ID
- (void) setAppId: (NSString *) newAppId
{
    appId = newAppId;
}

//return the App ID
- (NSString *) appId
{
    return appId;
}

//set the App Secret
- (void) setAppSecret: (NSString *) newAppSecret
{
    appSecret = newAppSecret;
}

//return the App Secret
- (NSString *) appSecret
{
    return appSecret;
}

//return the login URL
- (NSString *) getLoginUrl
{
    return [NSString stringWithFormat:@"%@?app_id=%@", OAUTH_URL, appId];
}

//set the Code
- (void) setCode:(NSString *) newCode
{
    code = newCode;
}

- (id) initKeySecret:(NSString *)newAppId
                    :(NSString *)newAppSecret
{
    appId = newAppId;
    appSecret = newAppSecret;
    
    return self;
}

//return the Access URL
- (NSString *) getAccessToken
{
    return [ self curlPost:[NSString stringWithFormat:@"%@?app_id=%@&app_secret=%@&code=%@", ACCESS_TOKEN_URL, appId, appSecret, code]: nil ];
}

@end