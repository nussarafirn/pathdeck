# PathDeck
**Team #11**  Firn Tieanklin and Kieu Trihn

## Features
- Users will be able to login using the registered username and password credentials. 
- If not yet register, users can create an account by clicking signup on the main menu page. 
- Users will be able to see all the past notes indicating who they were in contact with, those people's phone informaiton including emails and phone numbers, date that they interacted, and location that they met. These will be retrieved from the database using web services. 

## User Stories

### High Priority
- As a person that meets friends and family often, I want to be able to track whom I was with, so that I can keep track and look back into my diary to see who I was with and what I was doing at the time.
-  As a person who concerns about my privacy, I as a user want to be able to sign in authentication to use the service and sign out after I finish. So that I can make sure no one else other than me has access to my personal information regarding people that I was in contact with and when/where I have been. 
- As a user, I want to be able to sign up for a new account so that I can have a new account when I need it for other purposes.

### Low Priority
- As a user, I want to be able to choose which notes I want to use so that I can separate my memo notes and covid. 

### Errors
We got the dependencies error when trying to push the the notes table onto the database. It throws an error on needing the '@protobufjs/utf8@1.1.0' to be installed, but we have checked under nodes_modules directory, and it seems like we already have it there. So we were not sure what went wrong. That being said, the first high priority of showing the covid notes could not be showed and will throw NullPointerException because we could not add a new note to show on the Covid note (after click Add Notes > Covid Note). The note is currently empty for this testing user, therefore, it then throws NullPointerException when retreiving an empty NoteID. We did not have enough time figure it out, but this will be carried to the next sprint.  