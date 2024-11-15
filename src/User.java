import java.util.*;
import java.io.Serializable;

/**
 * User Class
 * This class describes Users on the platform. They have a unique bio, username, password, id, and pfp.
 * Each User also has their posts, friends, and other users blocked stored in their fields.
 * This class provides functionality to add friends, block users, remove friends and unblock users.
 * It implements the UserInterface and is serializable, allowing it to be saved and transferred.
 *
 * @author zaynrekhi
 * @author melody
 * @author srimadur
 * @author braydenbrafford
 * @author nothanlee
 * @version 1.0.0
 *
 * @version 1.0.0
 */
public class User implements UserInterface, Serializable {

    //Fields

    private ArrayList<Post> posts; //List of user's posts
    private ArrayList<User> friendsList; //src.User on the friend list
    private ArrayList<User> blockedList; //src.User on the blocked list
    private int userID;
    private String pfpURL;
    private String bio;
    private String userName;
    private String password;
    private static int userIDCounter;

    //Constructor
    public User(String theUserName, String thePassword, String bio) {
        try {
            if (theUserName.equals(" ") || thePassword.equals(" ")) {
                throw new UserCredentialsException("Username and password cannot be empty!");
            } else if (theUserName.length() < 6 || thePassword.length() < 6) {
                throw new UserCredentialsException("Username and password must be at least 6 characters!");
            } else if (theUserName.contains(" ") || thePassword.contains(" ")) {
                throw new UserCredentialsException("Username or password contains spaces!");
            }

            this.userID = userIDCounter;
            userIDCounter++;
            this.userName = theUserName;
            this.password = thePassword;
            this.bio = bio;

            this.posts = new ArrayList<>();
            this.friendsList = new ArrayList<>();
            this.blockedList = new ArrayList<>();
            this.pfpURL = "";
        } catch (UserCredentialsException e) {
            e.printStackTrace();
        }

    }

    //Getters

    public ArrayList<Post> getPosts() {
        return posts;
    }

    public ArrayList<User> getFriends() {
        return friendsList;
    }

    public ArrayList<User> getBlocked() {
        return blockedList;
    }

    // Method to get a combined feed of posts from all friends
    public ArrayList<Post> getFriendsFeed() {
        ArrayList<Post> friendsFeed = new ArrayList<>();
        for (User friend : friendsList) {
            friendsFeed.addAll(friend.getPosts()); // Add each friend's posts to the feed
        }
        return friendsFeed;
    }

    public int getUserID() {
        return userID;
    }

    public String getPfpURL() {
        return pfpURL;
    }

    public String getBio() {
        return bio;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    // Setters

    public void setPosts(ArrayList<Post> posts) {
        this.posts = posts;
    }

    public void setPfpURL(String pfpURL) {
        this.pfpURL = pfpURL;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public boolean setUserName(String theUserName) {
        try {
            if (theUserName.equals(" ")) {
                throw new UserCredentialsException("Username and password cannot be empty!");
            } else if (theUserName.length() < 6) {
                throw new UserCredentialsException("Username and password must be at least 6 characters!");
            } else if (theUserName.contains(" ")) {
                throw new UserCredentialsException("Username or password contains spaces!");
            }
            this.userName = theUserName;
            return true;
        } catch (UserCredentialsException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean setPassword(String thePassword) {
        try {
            if (thePassword.equals(" ")) {
                throw new UserCredentialsException("Username and password cannot be empty!");
            } else if (thePassword.length() < 6) {
                throw new UserCredentialsException("Username and password must be at least 6 characters!");
            } else if (thePassword.contains(" ")) {
                throw new UserCredentialsException("Username or password contains spaces!");
            }
            this.password = thePassword;
            return true;
        } catch (UserCredentialsException e) {
            e.printStackTrace();
            return false;
        }
    }

    //Need to implement other methods
    public void block(User toBeBlocked) {
        if (!hasBlocked(toBeBlocked)) {
            blockedList.add(toBeBlocked);
        }
    }

    public void addFriend(User toBeFriended) {
        if (!isFriendOf(toBeFriended)) {
            friendsList.add(toBeFriended);
        }
    }

    public void removeFriend(User toBeRemoved) {
        if (isFriendOf(toBeRemoved)) {
            friendsList.remove(toBeRemoved);
        }
    }
    public void unBlock(User toBeRemoved) {
        if (this.hasBlocked(toBeRemoved)) {
            blockedList.remove(toBeRemoved);
        }
    }

    public void post(Post toPost) {
        posts.add(toPost);
    }

    public boolean removePost(Post toRemove) {
        if (posts.contains(toRemove)) {
            posts.remove(toRemove);
            return true;
        }
        return false;
    }

    public boolean hasBlocked(User blockedUser) {
        return getBlocked().contains(blockedUser);
    }

    public boolean isFriendOf(User friend) {
        return getFriends().contains(friend);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        User user = (User) obj;
        return userName.equals(user.userName);
    }
}
