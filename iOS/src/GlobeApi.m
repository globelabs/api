//
//  GlobeApi.m
//  GL Wrapper IOS
//
//  Created by SuperDan on 11/30/13.
//  Copyright (c) 2013 SuperDan. All rights reserved.
//

#import "GlobeApi.h"
#import "Auth.h"
#import "Sms.h"
#import "Payment.h"

@implementation GlobeApi
- (Auth *) auth: (NSString *) key
               : (NSString *) secret
{
    return [[Auth alloc ]initKeySecret:key:secret];
}
- (Sms *) sms: (NSString *) shortCode
{
    return [[Sms alloc] initCode:shortCode];
}
- (Payment *) payment: (NSString *) toCharge
                     : (NSString *) token
{
    return [[Payment alloc] init:@"v1" :toCharge :token];
}
- (NSString *)curlPost:(NSString *) postUrl
                      : (NSDictionary *) fields
{
    NSURL *URL = [NSURL URLWithString: postUrl];
    NSMutableURLRequest *request = [NSMutableURLRequest requestWithURL:URL];
    // Set request type
    request.HTTPMethod = @"POST";
    
    NSMutableArray *array = [[NSMutableArray alloc] init];;
    // Set params to be sent to the server
    for(id key in fields) {
        [array addObject:[[NSString stringWithFormat:@"%@=%@", key, [fields objectForKey:key]] stringByAddingPercentEscapesUsingEncoding:NSUTF8StringEncoding]];
    }
    NSString *params = [NSString stringWithFormat:@"%@", [array componentsJoinedByString:@"&"]];    
    NSData *data = [params dataUsingEncoding:NSUTF8StringEncoding];
    
    [request addValue:@"8bit" forHTTPHeaderField:@"Content-Transfer-Encoding"];
    [request addValue:@"application/x-www-form-urlencoded" forHTTPHeaderField:@"Content-Type"];
    [request addValue:[NSString stringWithFormat:@"%lu", (unsigned long)[data length]] forHTTPHeaderField:@"Content-Length"];
    [request setHTTPBody:data];
    
    NSData *returnData = [NSURLConnection sendSynchronousRequest:request returningResponse:nil error:nil];
    NSString *returnString = [[NSString alloc] initWithData:returnData encoding:NSUTF8StringEncoding];
    
    return returnString;
}
- (NSString *)curlGet:(NSString *) getUrl
                      : (NSDictionary *) fields
{
    NSMutableArray *array = [[NSMutableArray alloc] init];
    for(id key in fields) {
        [array addObject:[[NSString stringWithFormat:@"%@=%@", key, [fields objectForKey:key]] stringByAddingPercentEscapesUsingEncoding:NSUTF8StringEncoding]];
    }
    
    NSString *params = [NSString stringWithFormat:@"%@", [array componentsJoinedByString:@"&"]];    
    
    NSURL *URL = [NSURL URLWithString: [NSString stringWithFormat: @"%@?%@",getUrl,params]];
    NSMutableURLRequest *request = [NSMutableURLRequest requestWithURL:URL];
    // Set request type
    request.HTTPMethod = @"GET";
    
    [request addValue:@"8bit" forHTTPHeaderField:@"Content-Transfer-Encoding"];
    [request addValue:@"application/x-www-form-urlencoded" forHTTPHeaderField:@"Content-Type"];
    
    NSData *returnData = [NSURLConnection sendSynchronousRequest:request returningResponse:nil error:nil];
    NSString *returnString = [[NSString alloc] initWithData:returnData encoding:NSUTF8StringEncoding];
    
    return returnString;
}
@end
