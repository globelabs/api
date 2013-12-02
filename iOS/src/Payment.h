//
//  Payment.h
//  GL Wrapper IOS
//
//  Created by SuperDan on 12/1/13.
//  Copyright (c) 2013 SuperDan. All rights reserved.
//

#import "GlobeApi.h"

@interface Payment : GlobeApi
{
    NSString *version;
    NSString *toCharge;
    NSString *token;
    NSString *description;
    NSString *clientCorrelator;
}
- (id) init: (NSString*) version
           : (NSString *) toCharge
           : (NSString *) token;
- (NSString *) charge: (float) amount
                     : (NSString *) refNo;
- (id) setDescription: (NSString *) desc;
@end
