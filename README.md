#Spam

An Instant Messaging application server written leveraging Actors

###Goals

######Offline messaging

Instant messaging server which works on the Actor level.  A User is represented by an Actor, and once registered will always remain
live in the system.  The User will, in this way, always be "online" and available to recieve messages.  

######User search

Given Users are always online, you will also be able to search for existing Users in a directory lookup mechanism.

######Multi Client

N number of Client Actors can connect simultaneously to said User Actor to be replayed previous messages and receive real-time incoming messages.  
They can then also initiate messages to other Users.

######Preserved history

History will be local to each Actor, and serialised to disk.  History can be replayed for any date range for any particular User, or searched for
specific details.  It will be stored in an encoded format so it's not readily searchable by anyone other than the connecting Client.

######Scheduled messaging

You can pre-program some messages to occur at certain times.  For example "Tell Dave at 11am tomorrow to bring me my Sandwich" - this will generate
a scheduled message to be delivered to Dave's User Actor at 11am.


######Broadcast

Given the nature of the technology you could also allow Users to change their handles, and have this change broadcast to all current connect
Clients who care about this User.

######Profiles

One powerful feature is to give each Client a "profile".  In this way you could associate certain Users with certain profiles - so that messages
from "Dave" would only ever goto your "work" or "mobile" profile(s).  This gives a way to route messages only to certain devices, as well as group
conversations logically.  Profiles can also be time bound - for instance "only receive messages to this device after 5:30pm on weekdays and on weekends"

###Running Spam

To run and test it use SBT invoke: 'sbt run'
