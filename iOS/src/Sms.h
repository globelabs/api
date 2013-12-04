//
//  Sms.h
//  GL Wrapper IOS
//
//  Created by SuperDan on 11/28/13.
//  Copyright (c) 2013 SuperDan. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "GlobeApi.h"

@interface Sms : GlobeApi
{
    NSString *shortCode;
}

- (id) initCode:(NSString *) shortCode;
- (NSString *) sendMessage:(NSString *) token
                          :(NSString *) sendTo
                          :(NSString *) message;
                            
@end
