//
//  Auth.h
//  GL Wrapper IOS
//
//  Created by SuperDan on 11/28/13.
//  Copyright (c) 2013 SuperDan. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "GlobeApi.h"

@interface Auth : GlobeApi
{
    NSString *appId;
    NSString *appSecret;
    NSString *code;
}

//set the APP ID
- (void) setAppId: (NSString *) newAppId;
//get the APP ID
- (NSString *) appId;

//set the APP SECRET
- (void) setAppSecret: (NSString *) newAppSecret;
//get the APP SECRET
- (NSString *) appSecret;

//get the Login URL
- (NSString *) getLoginUrl;

//set the Access Token
- (void) setCode: (NSString *) newCode;
//get the Access Token
- (NSString *) getAccessToken;

- (id) initKeySecret:(NSString *) newAppId
                : (NSString *) newAppSecret;

@end
