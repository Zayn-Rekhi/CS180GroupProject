import java.util.*;
import java.io.Serializable;

public class User implements UserInterface, Serializable {

    //Fields

    private ArrayList<String> permissions; //Direct Messaging
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
    public User(String userName, String password, String bio) {
        try {
            if (userName.equals(" ") || password.equals(" ")) {
                throw new UserCredentialsException("Username and password cannot be empty!");
            } else if (userName.length() < 6 || password.length() < 6) {
                throw new UserCredentialsException("Username and password must be at least 6 characters!");
            } else if (userName.contains(" ") || password.contains(" ")) {
                throw new UserCredentialsException("Username or password contains spaces!");
            }

            this.userID = userIDCounter;
            userIDCounter++;
            this.userName = userName;
            this.password = password;
            this.bio = bio;

            this.permissions = new ArrayList<>();
            this.posts = new ArrayList<>();
            this.friendsList = new ArrayList<>();
            this.blockedList = new ArrayList<>();
            this.pfpURL = "";
        } catch (UserCredentialsException e) {
            e.printStackTrace();
        }

    }

    //Getters
    public ArrayList<String> getPermissions() {
        return permissions;
    }

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
    public void setPermissions(ArrayList<String> permissions) {
        this.permissions = permissions;
    }

    public void setPosts(ArrayList<Post> posts) {
        this.posts = posts;
    }

    public void setPfpURL(String pfpURL) {
        this.pfpURL = pfpURL;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    //Need to implement other methods
    public void Block(User toBeBlocked) {
        if (!isBlockedBy(toBeBlocked)) {
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

    public void Post(Post toPost) {
        posts.add(toPost);
    }

    public boolean isBlockedBy(User blockedUser) {
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

    @Override
    public int hashCode() {
        return Objects.hash(userName);
    }
}
