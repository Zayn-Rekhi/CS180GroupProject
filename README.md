## Compile and Run Methods

1. All of our `.java` files are in the `src` folder which contains testcases, interfaces, classes, and more. 
2. We do **NOT** have `main` method for phase one.
3. The following is a list of our testcase files:
   - `CommentsTests.java`
   - `DatabaseTests.java`
   - `DataFormatExceptionTests.java`
   - `PostTests.java`
   - `UserAlreadyExistsTests.java`
   - `UserCredentialsExceptionTests.java`
   - `UserTests.java`

## Class Descriptions

### Comment.java
The comment class represents a comment made by a single user who "owns" the comment.
Each comment is connected to a single post upon which it is an accessory.
Comments have functionality to be created on a post, to be edited by the comment owner, 
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
The database class represents the database where users can be added and deleted from. The fundmental 
backbone of this functionality is that each User object and its subclasses implement serializable allowing
us to store their data in text files efficiently. The central part of this class is that there is an ArrayList of 
users that is static and completely threadsafe. In the future, this will allow multiple clients to connect to the data
base so that they can read and writer from it at the same time. Additionally, this file handles user authentication by 
having methods that can create users, check their username and password, and more. The updateDateFile method writers
all the data from the users ArrayList to the specified text file, the loadUsers method reads all the users from the 
specified input file into the users ArrayList to ensure data persistency, the check login method
checks if the username and password exist in the database, the adduser method adds the user to the ArrayList,
the modify user method replaces prevUser with a newUser, the removeUser method removes a user, the createuser
method creates a user, the find user method finds a user based on username, the get users method returns all the users, 
and the clear method clears the entire database. This class works in conjunction with the Users class in order to store 
and manage user data. 
There are 6 test cases:
1. Ensures that all users are loaded from a text file
2. Ensures functionality of verifying login credentials
3. Ensures functionality of creating new users 
4. Ensures functionality of modifying a user by replacing it with new User object with new information
5. Ensures functionality of removing user
6. Ensures functionality of writing users to text file and then reading from it to ensure persistency

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
4,5.
6. Ensures functionality of displaying the post
7. Ensures functionality of commenting on the post
8. Ensures functionality of displaying the post with its comments
9. Ensures functionality of removing comments from the post


### User.java
The user class represents a User. 
Each user contains fields  username, bio, password, profile picture, and ID. 
Each user also maintain lists of permissions, friends, blocked users, and posts.
The class has functionalities to access the posts from other users.
Users have accessors and mutators for each of the fields
There are 10 test cases
1. Ensures functionality of initializing users
2. Ensures functionality of the accessor and mutator methods
3. Ensures functionality of adding user as friends correctly
4. Ensures functionality of removing user as friends correctly
5. Ensures functionality of blocking a user correctly
6. Ensures functionality of unblocking a user correctly
7. Ensures functionality of updating the bio correctly
8. Ensures functionality of retrieving and setting the permissions correctly
9. Ensures functionality of accessing posts correctly
10. Ensures functionality of adding and retrieving a user's post to the user correctly


### DateFormatException.java
The DateFormatException is thrown whenever a date has been created incorrectly. The correct
format for a date is in 00-00-0000 (month-day-year). If a date is passed has not been passed
in this format, then this Exception will be thrown. It is mainly utilized in the Post and Comment
class for keeping track of when a comment or a post was created. It is thrown in the constructor.
There is one test case:
1. Checks if the message passed by the parameter matches the message it throws

### UserAlreadyExistsException.java
The UserAlreadyExistsException is thrown whenever a user that already exists in the database is attempted to be added again.
Each user in the database is supposed to be distinct and unique, so none should be added twice.
It is utilized in the database class to make sure a user that already exists isn't added again, and is thrown in the createUser method.
There is one test case:
1. Checks if the message passed by the parameter matches the message it throws

### UserCredentialsException.java
The UserCredentialsException is thrown whenever a new user is created and their username or password doesn't meet
the bare minimum requirements. Each username or password is supposed to be 6 characters long and contains no spaces. 
It is thrown in the User constructor if the parameter of username and password is not long enough
There is one test case:
1. Checks if the message passed by the parameter matches the message it throws

