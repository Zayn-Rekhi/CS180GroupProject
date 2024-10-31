import java.util.ArrayList;

public interface UserInterface{
 
    //Getters
    ArrayList<String> getPermissions();

    ArrayList<Post> getPosts();

    ArrayList<User> getFriends();

    ArrayList<User> getBlocked();

    int getUserID();

    String getPfpURL();

    String getBio();

    String getUserName();

    String getPassword();

    // Setters
    void setPermissions(ArrayList<String> permissions);

    void setPosts(ArrayList<Post> posts);

    void setFriends(ArrayList<User> friends);

    void setBlocked(ArrayList<User> blocked);

    void setUserID(int userID);

    void setPfpURL(String pfpURL);

    void setBio(String bio);

    void setUserName(String userName);

    void setPassword(String password);

    //Need to implement other methods
    boolean block(User toBeBlocked);
    boolean addFriend(User toBeFriended);
    boolean removeFriend(User toBeRemoved);
    void post(Post toPost);
    boolean isBlockedBy(User blocky);
    boolean isFriendOf(User friendy);
}