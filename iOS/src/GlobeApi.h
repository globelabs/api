//
//  GlobeApi.h
//  GL Wrapper IOS
//
//  Created by SuperDan on 11/30/13.
//  Copyright (c) 2013 SuperDan. All rights reserved.
//

#import <Foundation/Foundation.h>

@class Auth;
@class Sms;
@class Payment;
@interface GlobeApi: NSObject
- (NSString *) curlPost: (NSString *) postUrl
                       : (NSDictionary *) fields;
- (NSString *) curlGet: (NSString *) getUrl
                      : (NSDictionary *) fields;
- (Auth *) auth: (NSString *) key
               : (NSString *) secret;
- (Sms *) sms: (NSString *) shortCode;
- (Payment *) payment: (NSString *) toCharge
                     : (NSString *) token;
@end
