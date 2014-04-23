//
//  ARSGlobals.h
//  Arsfest
//
//  Created by Thibaud Robelain on 20/02/14.
//  Copyright (c) 2014 dtu. All rights reserved.
//

// Constants
#define kNumberOfLocations 5

// Parse
#define kParseApplicationId @"vDLtONCuZIlrt6wEnfHrFbIDVhoW3cxUCAGEx2j5"
#define kParseClientKey @"ZLgCiJAv5TkA4ChPgeLSnKb5S4dszCMBuVjypPcc"

// Color constants
#define  kArsfestColor   [UIColor colorWithRed:84/255.f green:41/255.f blue:124/255.f alpha:1]
//#define  kArsfestColor   [UIColor colorWithRed:128/255.f green:0/255.f blue:128/255.f alpha:1]
#define kFriendCellColor [[UIColor colorWithRed:213/255.f green:213/255.f blue:213/255.f alpha:1.0f] CGColor]


// Image constants
#define kBackgroundImage [UIImage imageNamed:@"default_background.png"]

// Key constants
#define INTRO_SHOWN_ONCE @"IntroShownOnce"
#define LAST_FACEBOOK_REFRESH @"lastFacebookRefresh"
#define LAST_PARSE_REFRESH @"lastParseRefresh"
#define REFRESH_PARSE_AFTER 5

// Friend list table view constant
#define FRIEND_CELL_HEIGHT 71

// Location refresh timer
#define REFRESH_RATE_LOCATION 60

// Dates
#define ARSFEST_START_DATE [NSDate dateWithYear:2014 month:5 day:9 hour:15]
#define ARSFEST_REAL_START_DATE [NSDate dateWithYear:2014 month:5 day:9 hour:17]
#define ARSFEST_END_DATE [NSDate dateWithYear:2014 month:5 day:10 hour:5]
#define ARSFEST_REAL_END_DATE [NSDate dateWithYear:2014 month:5 day:10 hour:3]

// Informations

#define INFO_ARSFEST_WELCOME @"The Commemoration Party is the festive peak of the academic year, where achievements, perspectives and collaborations are celebrated with the students, employees and partners of the university. \nDTU also highlights select achievements of the past year by the awarding academic honours. \nThe Commemoration Party can be divided into three: The official part, followed by dinner and then a large ball. \nDTU and Polytechnical Association hope you have a fantastic evening."

#define INFO_FACEBOOK @"Did you know? It’s possible to find your friends during the party. \n\nSimply connect with Facebook in the Location settings and you will see a list of all your connected friends during the party, along with their location."

#define INFO_WIFI @"You can locate yourself on the map when you are connected to DTU’s Eduroam Wi-Fi. \n\nThe accuracy of this service depends on the signal strength of the Wi-Fi to which you are connected."
