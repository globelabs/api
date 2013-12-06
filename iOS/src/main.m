//
//  main.m
//  GL Wrapper IOS
//
//  Created by SuperDan on 11/28/13.
//  Copyright (c) 2013 SuperDan. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "Auth.h"
#import "Sms.h"
#import "Payment.h"

int main(int argc, const char * argv[])
{

    NSString *APP_ID = @"97nG9SMy74jC7ocyn6i7xyCd5ny5SpXd";
    NSString *APP_SECRET = @"fd64841f05085429f5b9ea91679fe62a7e2373afdfc6b3381685ecfb317819f1";
    
    @autoreleasepool {
        GlobeApi *globe = [[GlobeApi alloc] init];
        
        Auth *auth = [globe auth:APP_ID :APP_SECRET];
        [auth setCode: @"p6eUy4LKrh76xoGuAKKp9Ub75q5I5nA86Hrb9BjSKbrd7h49jMgHgMgXbIea9xkI4bgnLHKz5KLUzrB5RuokERLunR7p4HXr6ykFoaxRAS76KKdF9RzobCBra8RfA7cxbeia6zfazz5jCqXKokFkAx8bSMR6a8Fn77KeHbLEoXuMkBEKu7d5r4Up5gk8HGa9EqIAygndI8xjXGHBxraLhrA9XjS8eApaHap5RGIkRKbnUMgxz6uABL7Lh7zpRbUM"];
        
        NSString *authResponse = [auth getAccessToken];
        NSLog(@"%@", authResponse);
        
        Sms *sms = [globe sms:@"4448"];
        NSString *smsResponse = [sms sendMessage: @"X1obYywA-O5GRAEv4vEUrk-MvJF6P3uNkgLQS3roQUw"
                        : @"9176234236"
                        : @"bello!"];
        NSLog(@"%@",smsResponse);
        
        NSLog(@"%@",[sms getMessages]);
        
        Payment *payment = [globe payment:@"9176234236" :@"X1obYywA-O5GRAEv4vEUrk-MvJF6P3uNkgLQS3roQUw"];
        NSString *paymentResponse = [payment charge:0 :@"44481000009"];
        NSLog(@"%@",paymentResponse);
    }
    return 0;
}

