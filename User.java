import java.util.ArrayList;
import java.io.Serializable;
public class User implements Serializable {

    //Fields

    private ArrayList<String> permissions; //Not sure what this does
    private ArrayList<Post> posts; //List of user's posts
    private ArrayList<User> friends; //User on the friend list
    private ArrayList<User> blocked; //User on the blocked list
    private int userID; 
    private String pfpURL;
    private String bio;
    private String userName;
    private String password;
    private static int userIDCounter = 0;

    //Constructor

    public User(String userName, String password, String bio) {
        this.userID = userIDCounter;
        userIDCounter++;
        this.userName = userName;
        this.password = password;
        this.bio = bio;

        this.permissions = new ArrayList<>();
        this.posts = new ArrayList<>();
        this.friends = new ArrayList<>();
        this.blocked = new ArrayList<>();
        this.pfpURL = "";

    }

    //Methods

    //Getters
    public ArrayList<String> getPermissions() {
        return permissions;
    }

    public ArrayList<Post> getPosts() {
        return posts;
    }

    public ArrayList<User> getFriends() {
        return friends;
    }

    public ArrayList<User> getBlocked() {
        return blocked;
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

    public void setFriends(ArrayList<User> friends) {
        this.friends = friends;
    }

    public void setBlocked(ArrayList<User> blocked) {
        this.blocked = blocked;
    }

    public void setUserID(int userID) {
        this.userID = userID;
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
    public boolean block(User toBeBlocked) {
        if (blocked.contains(toBeBlocked)) {
            return false;
        }
        return blocked.add(toBeBlocked);
    }
    public boolean addFriend(User toBeFriended) {
        if (friends.contains(toBeFriended)) {
            return false;
        }
        return friends.add(toBeFriended);
    }
    public boolean removeFriend(User toBeRemoved) {
        return friends.remove(toBeRemoved);
    }
    public void post(Post toPost) {
        posts.add(toPost);
    }

    public boolean isBlockedBy(User blocky) {
        return blocky.getBlocked().contains(this);
    }
    public boolean isFriendOf(User friendy) {
        return friendy.getFriends().contains(this);
    }

}
