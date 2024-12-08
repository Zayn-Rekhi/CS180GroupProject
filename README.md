## Compile and Run Methods

1. All of our `.java` files are in the `src` folder which contains test cases, interfaces, classes, and more.
2. To test Network I/O, run the main method in `Main.java`, then run the main method in `Client.java`. To test the GUI 
first run the main Method in `Main.java`, then run the GUI in UserGUI.java.

    In terminal run these commands: cd src, javac Main.java, java Main
    open another terminal and: cd src, javac Client.java, java Client
    If you want to run the GUI: cd src, javac UserGUI.java, java UserGUI
4. The following is a list of our test case files:
   - `CommentsTests.java`
   - `DatabaseTests.java`
   - `DateFormatExceptionTests.java`
   - `PostTests.java`
   - `UserCredentialsExceptionTests.java`
   - `UserTests.java`
   - `ClientTests.java`
   - `DataTransferTests.java`
   - `ServerTests.java`

## Class Descriptions

### BlogPostsPanel.java

The BlogPostsPanel class is a user interface component designed for the social media feed, built using Java Swing. It allows users to:

- View and interact with posts in a feed.
-Create new posts.
- Comment, like, or dislike posts and comments.
- Navigate to other sections of the platform, such as viewing their posts or managing friends.

Key Features:
1. Post Management:
Users can create posts by entering a title and description.
Posts are displayed with details, including the author, creation date, likes, and comments.
Dynamic updates allow new posts to be added to the feed in real-time.

2. Comment Interaction:
Comments can be added to posts.
Users can like, dislike, or delete comments (if permissions allow).
Dynamic updates allow new comments to appear instantly.

3. Likes and Dislikes:
Users can like or dislike posts and comments.
Buttons dynamically update based on whether the user has already interacted with the post/comment.
Navigation:

4. Navigation buttons allow users to switch to other sections:
Add Friends
View My Posts
Sign Out

5. Server Integration:
Communicates with the server using DataTransfer objects to handle actions like creating posts, adding comments, and updating likes/dislikes.






### Comment.java
The comment class represents a comment made by a single user who "owns" the comment.
Each comment is connected to a single post upon which it is an accessory.
Comments have the functionality to be created on a post, to be edited by the comment owner,
to be removed by either the comment or post owner, and to be liked or disliked by any user.
Each comment also keeps the information for the date it was commented and the date it was edited (if it was edited).
Each comment is displayed by a method that lists all the relevant information for the comment in an organized format.
Eah comment also has a unique id that helps with identification.
There are 7 test cases:
1. Ensures functionality of likes and dislikes
2. Ensures functionality of accessor and mutator methods in tandem
3. Ensures functionality of editing a message and its display
4. Ensures that format of a displayed comment is as intended
   5,6,7. Each of these tests ensure the functionality of deleting
   comments for the comment owner, the post owner, and another random user


### Database.java
The database class represents the database where users can be added and deleted from. The fundamental
backbone of this functionality is that each User object and its subclasses implement serializable allowing
us to store their data in text files efficiently. The central part of this class is that there is an ArrayList of
users that is static and completely threadsafe. In the future, this will allow multiple clients to connect to the data
base so that they can read and writer from it at the same time. Additionally, this class handles user authentication by
having methods that can create users, check their username and password, and more. The updateDateFile method writers
all the data from the users ArrayList to the specified text file, the loadUsers method reads all the users from the
specified input file into the users ArrayList to ensure data persistency, the check login method
checks if the username and password exist in the database, the adduser method adds the user to the ArrayList,
the modify user method replaces prevUser with a newUser, the removeUser method removes a user, the createuser
method creates a user, the find user method finds a user based on username, the get users method returns all the users,
and the clear method clears the entire database. This class works in conjunction with the Users class in order to store
and manage user data.
There are 8 test cases:
1. Ensures functionality of clearing the entire database 
2. Ensures that all users are loaded from a text file
3. Ensures functionality of verifying login credentials
4. Ensures functionality of creating new users
5. Ensures functionality of searching for users
5. Ensures functionality of modifying a user by replacing it with new User object with new information
6. Ensures functionality of removing user
7. Ensures functionality of writing users to text file and then reading from it to ensure persistency



### FriendsPanel.java
The FriendsPanel class provides a user interface for managing friends and blocked users
within the social media application. It allows users to search for others, perform 
actions on the results, and view their current friends and blocked users. This panel is
designed using Java Swing and interacts with the backend server for real-time updates.

Key Features:
- Search Functionality:
Users can search for other users by entering a username in the search field.
Displays search results with the user's profile details (e.g., username, bio) and friendship/blocking status.
- Actions on Search Results:

Users can add friends, remove friends, block users, or unblock users based on their search results.
Actions are performed using a dialog box with options for interaction.
Friends and Blocked Users List:

Displays a list of the user's current friends with their usernames and bios.
Displays a list of blocked users in a distinct format to differentiate them.
- Navigation:

Includes a "Back to Feed" button to return to the main blog feed (BlogPostsPanel).
- Server Integration:

Uses DataTransfer objects to perform server-side actions such as searching for users, adding/removing friends, and blocking/unblocking users.
- Key Methods:
toBlogPanel(User user): Navigates back to the blog feed (BlogPostsPanel) after updating the user's interactions.
Search Action:
Sends a search request to the server using DataTransfer.
Handles responses and displays the results with options to interact with the searched user.
Friend and Blocked User List:
Dynamically creates labels for friends and blocked users using their username and bio.
Formats friends in green and blocked users in red for clarity.


### LoginPanel.java

The LoginPanel class provides the user interface for logging into the social media platform 
or creating a new user account. It features a user-friendly layout with fields for entering 
credentials and buttons to either log in or create a new account. The panel integrates backend
communication to validate user credentials or create new profiles and seamlessly navigates to 
the blog feed upon success.

Key Features:

- User Authentication:
Login: Validates the username and password against the backend server using DataTransfer.
Displays error messages if credentials are invalid or the server denies access.

- Account Creation:
Allows users to create a new account by entering a username, password, and bio.
Sends the information to the server, which validates and creates the user profile.
Navigates to the blog feed upon successful creation.

- Error Handling:
Prompts clear feedback if fields are empty or authentication fails, ensuring a smooth user experience.

- Navigation:
Automatically navigates to the BlogPostsPanel after a successful login or account creation.

- Server Integration:
Uses DataTransfer objects to handle user actions, ensuring that the backend processes login and creation requests.

- Key Methods:
toBlogPanel(User user): Navigates to the BlogPostsPanel and initializes the feed for the logged-in user.
Login Button Logic:
Sends a USER GETLOGIN request to the server with the provided username and password.
Updates the session if the login is successful.
Create User Button Logic:
Sends a USER CREATENEWUSER request to the server with the provided username, password, and bio.
Automatically logs the user in and navigates to the blog feed if the account is successfully created.


### MyPostsPanel.java
The MyPostsPanel class is a user interface component that allows the logged-in user to 
view their own posts. It provides a clean and organized display of the user's posts, 
including key details such as the post title, description, creation date, and interaction 
statistics (likes and dislikes). Users can also navigate back to the main feed.

Key Features:
- Post Display:
Fetches and displays all posts created by the logged-in user.
Posts are organized in a scrollable view with details such as title, description, creation date, likes, and dislikes.
If the user has no posts, a message is displayed instead.

- Server Integration:
Uses the POST GETUSERPOSTS command to retrieve the logged-in user's posts from the backend server via DataTransfer.

- Navigation:
Provides a "Back to Feed" button that navigates the user back to the main feed (BlogPostsPanel).
Key Methods:
fetchUserPosts():
Sends a request to the server to fetch all posts created by the logged-in user.
Displays an error message if the posts cannot be retrieved.
createPostPanel(Post post):
Dynamically generates a panel for each post, displaying its title, description, creation date, and interaction statistics (likes and dislikes).


### Post.java
The post class represents a post made by a specific user.
Each post will contain comments that reflect on the post.
Each post has functionality to be created, either with text, an image, or both.
Posts also contain comments, likes, and dislikes. Comments can be removed.
Posts have accessors and mutators, although they are not intended to be implemented as option for the platform itself.
Posts also contain data for a title, date, and unique ID.
There are 9 test cases:

1. Ensures functionality of initializing of the posts
2. Ensures functionality of accessor and mutator methods in tandem
3. Ensure functionality of constructor without an image
4. Ensure functionality of add and removing likes
5. Ensure functionality of add and removing dislikes
6. Ensures functionality of displaying the post
7. Ensures functionality of commenting on the post
8. Ensures functionality of displaying the post with its comments
9. Ensures functionality of removing comments from the post

### ViewPostsPanel.java

ViewPostsPanel.java
The ViewPostsPanel class provides a user interface for the logged-in user to view and manage their posts. It organizes posts in a scrollable layout and allows users to perform actions such as hiding, showing, deleting posts, and managing comments. The panel ensures dynamic updates and maintains seamless navigation with the rest of the application.

Key Features:
- Post Display and Management:
Displays the user's posts with details such as title, description, creation date, author, likes, and comments.
Provides buttons to:
Delete: Removes the post permanently.
Hide/Show: Toggles the visibility of a post.

- Comment Management:
Displays all comments associated with a post.
Allows the user to delete comments if permitted.

- Dynamic Updates:
Updates the display dynamically based on actions like hiding/showing posts and deleting posts or comments.
Communicates changes to the backend server for real-time synchronization using DataTransfer objects.

- Navigation:
Includes a "Back" button to navigate to the main feed (BlogPostsPanel).

Key Methods:
createCommentPanel(Comment comment, Post post, JPanel commentsPanel):
    Dynamically generates a panel for each comment with options to view likes and delete the comment (if the user has permission).
toViewPostsPanel():
    Refreshes the current panel to show the updated list of posts after an action.
toBlogPanel(User user):
    Navigates back to the main blog feed and refreshes the user's feed.

### User.java
The user class represents a User.
Each user contains fields  username, bio, password, profile picture, and ID.
Each user also maintains lists of friends, blocked users, and posts.
The class has functionalities to access the posts from other users.
Users have accessors and mutators for each of the fields. There are 9 test cases


1. Ensures functionality of initializing users
2. Ensures functionality of the accessor and mutator methods
3. Ensures functionality of adding user as friends correctly
4. Ensures functionality of removing user as friends correctly
5. Ensures functionality of blocking a user correctly
6. Ensures functionality of unblocking a user correctly
7. Ensures functionality of updating the bio correctly
8. Ensures functionality of retriveving a feed of a friend's posts
9. Ensures functionality of adding many posts to a User at once


### DateFormatException.java
The DateFormatException is thrown whenever a date has been created incorrectly. The correct
format for a date is in 00-00-0000 (month-day-year). If a date has not been passed
in this format, then this Exception will be thrown. It is mainly utilized in the Post and Comment
class for keeping track of when a comment or a post was created. It is thrown in the constructor.
There are two test cases:
1. Checks if the message passed by the parameter matches the message it throws
2. Ensures an incorrectly formatted date passed into Post and Comment does not throw an Exception

### UserAlreadyExistsException.java
The UserAlreadyExistsException is thrown whenever a user that already exists in the database is attempted to be added again.
Each user in the database is supposed to be distinct and unique, so none should be added twice.
It is utilized in the database class to make sure a user that already exists isn't added again, and is thrown in the createUser method.
There are two test case:
1. Checks if the message passed by the parameter matches the message it throws
2. Ensures that if the user already exists in the database, the create user function (Database.java) don't throw an exception

### UserCredentialsException.java
The UserCredentialsException is thrown whenever a new user is created and their username or password doesn't meet
the bare minimum requirements. Each username or password is supposed to be 6 characters long and contains no spaces.
It is thrown in the User constructor if the parameter of username and password is not long enough
There are 5 test cases:
1. (1-4) Checks if the message passed by the parameter matches the message it throws
2. (5) Ensures that invalid credentials passed in the create user function (Database.java) and User constructor (User.java) don't throw an exception

### Client.java
The Client class represents the client-side of the platform and allows the users to interact with the server through commands.  
It sends requests encapsulated in DataTransfer objects to the server and processes responses to update the database.  
It handles all the communication with the Server class through sockets and ensures the functionality of user operations.
It implements the ClientInterface which contains all the methods that are used in this class.
There are 8 test cases for the Client class:
1. Ensures the constructor works correctly
2. Ensures that succesfully writing objects works as intended
3. Ensures that checking for a failure for writing objects works as intended
4. Ensures that waiting for a response works as intended
5. Ensures that checking for a failure for waiting for a response works as intended
6. Ensures that requesting a message works as intended.
7. Ensures that checking for a failure when requesting a message works as intended
8. Ensures closing functionality

### DataTransfer.java
The DataTransfer Class is used to send and recieve commands between the client and server.
It stores a message which is the command being given, and a value with is the object or 
array of objects being passed in with the given command (such as the username and password when creating a user).
There are two test case:
1. Ensure the constructor and getMessage method work in regards to the message of the datatransfer.
2. Ensures that the constructor and getValue method work in regards to the value of the datatransfer.
   
### Server.java
The Server class stores the database and handles all commands that affect the database.
It will process commands given through DataTransfer objects in order to create and update users, posts, and comments.
It has functionality to check for commands that can update the database in every possible way it needs to be such as users being created, 
posts being commented on, those comments being edited, friends being added, etc.

There are twenty-two test case:
1. Ensures that the create-user command succesfully addes the users to the database with the correct data
2. Ensures that creating a user with invalid credentials is checked succesfully and doesn't affect the database
3. Ensures correct functionality with the getLogin command for the given user
4. Ensures that the removeUser command successfully finds and removes the given user from the database
5. Ensures the modifyUser command successfully finds and edits the given user
6. Ensures correct functionality with the addFriend command with a given user
7. Ensures correct functionality with removing a friend from a given user
8. Ensures correct functionality with blocking a friend
9. Ensures correct functionality with unblocking a friend
10. Ensures that creating a post for a user works correctly
11. Ensures that deleting a post works correctly for the given user and post
12. Ensures that editing a post works as it should
13. Tests the functionality of liking a post
14. Tests the functionality of unliking a post
15. Tests the functionality of disliking a post
16. Tests the functionality of undisliking a post
17. Ensures the ability to find posts only from a user's friends
18. Ensures the createComment command works as intended
19. Ensures the ability to delete a comment from the correct user
20. Ensures comment-editing functionality and success
21. Tests the functionality of liking a comment
22. Tests the functionality of disliking a comment

### UserGUI.java
The UserGUI class extends thread to be runnable to start the GUI and enable user interaction with the system. It utilizes the client class in order to interact with the server and database, and opens the login panel when run. There are 4 Panel classes used to move between the GUI and showcase the different parts of the platform.
