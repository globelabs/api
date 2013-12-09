//
//  Payment.m
//  GL Wrapper IOS
//
//  Created by SuperDan on 12/1/13.
//  Copyright (c) 2013 SuperDan. All rights reserved.
//

#import "Payment.h"

@implementation Payment
- (id) init: (NSString*) newVersion
           : (NSString *) newToCharge
           : (NSString *) newToken
{
    version = newVersion;
    toCharge = newToCharge;
    token = newToken;
    description = @"";
    return self;
}
- (id) setDescription: (NSString *) desc
{
    description = desc;
    
    return self;
}
- (NSString *) charge: (float) amount
                     : (NSString *) refNo
{
    NSMutableDictionary *dictionary = [[NSMutableDictionary alloc] init];
    [dictionary setObject: token forKey:@"access_token"];
    [dictionary setObject: toCharge forKey:@"endUserId"];
    [dictionary setObject: [NSString stringWithFormat:@"%.02f", amount] forKey:@"amount"];
    [dictionary setObject: refNo forKey:@"referenceCode"];
    [dictionary setObject: @"charged" forKey:@"transactionOperationStatus"];
    [dictionary setObject: description forKey:@"description"];
    
    return [self curlPost:@"http://devapi.globelabs.com.ph/payment/v1/transactions/amount"
                         :dictionary];
}
@end
