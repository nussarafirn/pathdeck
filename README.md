# PathDeck

## Features
- Users will be able to login using the registered username and password credentials. 
- If not yet register, users can create an account by clicking signup on the main menu page. 
- Users will be able to see all the past notes indicating who they were in contact with, those people's phone informaiton including emails and phone numbers, date that they interacted, and location that they met. These will be retrieved from the database using web services.
- User will be able to add their health information into the health page and the page will always update the newst data from the last time the user login as displaying the temperature, symptoms and test result. 
- All the notes are only displayed if the save date to the current dates are less than 14 days 
- User will be able to sent the email notification to all the people in the 14 that have been saved in the notes
## User Stories

### High Priority
US01: As a person that meets friends and family often, I want to be able to track whom I was with, so that I can keep track and look back into my diary to see who I was with and what I was doing at the time.  
US02: As a person who cares about others, I want the notes to keep track of my activities in the 14 days period so that I can quickly notify people when I get infected .
US03: As a person who is concerned about the COVID-19, I want to have an option to notify people who I got in contact with in the last 14 days or when I notice the symptoms. So that those people could get tested reducing the spread to their friends and family. 
US04: As a person who gets sick and has a headache often, I want to be able to track my health, symptoms, body temperature etc. So that I can differentiate the symbols between my regular headache and any additional symptoms that would relate to COVID-19. 
US05 - As a person who concerns about my privacy, I as a user want to be able to sign in authentication to use the service and sign out after I finish. So that I can make sure no one else other than me has access to my personal information regarding people that I was in contact with and when/where I have been. 
US06 As a user, I want to be able to sign up for a new account so that I can have a new account when I need it for other purposes.

### Low Priority
- As a user, I want to be able to choose which notes I want to use so that I can separate my memo notes and covid. 


### Testing Account
Here are the list of testing accounts to test the funtionality of the application. 

- User #1
    - Username: user
    - Password : 12345678
- User #2
    - Username: firntest
    - Password : 12345678

###SharedPreferences
The sharedPrefences save the username and wether if the account is set to remember. The sharereference have been user in all the activity and fragment class
to access to the username. The data is saved edu.tacoma.uw.finalproject.sign_in_file_prefs. 

### Errors
We got the dependencies error when trying to push the the notes table onto the database. It throws an error on needing the '@protobufjs/utf8@1.1.0' to be installed, but we have checked under nodes_modules directory, and it seems like we already have it there. So we were not sure what went wrong. That being said, the first high priority of showing the covid notes could not be showed and will throw NullPointerException because we could not add a new note to show on the Covid note (after click Add Notes > Covid Note). The note is currently empty for this testing user, therefore, it then throws NullPointerException when retreiving an empty NoteID. We did not have enough time figure it out, but this will be carried to the next sprint.  

Updated error fixed on 7/8/20

 The error has been fix from the backend code in Register.js inside the INSERT statement. The prvious version doesn't specify the parameters, but that was required since the user input were not directly map to the table fields. 

Updated 8/21/20

Bug fixed on adding a COVID note with invalid information. We have a toast message to handle the exception insteads of the throwing the exceptions. We also adding validation for the registration and health update information page by having an informative mesage telling users the expected format of the input. Lastly, the health page is now be able to view with defult values for the new users.
