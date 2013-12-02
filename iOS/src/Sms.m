//
//  Sms.m
//  GL Wrapper IOS
//
//  Created by SuperDan on 11/28/13.
//  Copyright (c) 2013 SuperDan. All rights reserved.
//

#import "Sms.h"
#import "GlobeApi.h"

@implementation Sms: GlobeApi
- (id) initCode:(NSString *)newShortCode
{
    shortCode = newShortCode;
    
    return self;
}
- (NSString *) sendMessage:(NSString *) token
                          :(NSString *) sendTo
                          :(NSString *) message
{
    NSMutableDictionary *dictionary = [[NSMutableDictionary alloc] init];
    [dictionary setObject: token forKey:@"access_token"];
    [dictionary setObject: sendTo forKey:@"address"];
    [dictionary setObject: message forKey:@"message"];
    [dictionary setObject: shortCode forKey:@"senderAddress"];
    
    return [ self curlPost:[NSString stringWithFormat:@"http://devapi.globelabs.com.ph/smsmessaging/v1/outbound/%@/requests", shortCode]: dictionary ];

}
@end
