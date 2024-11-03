import java.util.ArrayList;

public interface UserInterface {

    int userIDCounter = 0;

    // Getters
    int getUserID();
    String getUserName();
    String getPassword();
    String getBio();
    String getPfpURL();
    ArrayList<String> getPermissions();
    ArrayList<Post> getPosts();
    ArrayList<User> getFriends();
    ArrayList<User> getBlocked();
    ArrayList<Post> getFriendsFeed();

    // Setters
    void setUserName(String userName);
    void setPassword(String password);
    void setBio(String bio);
    void setPfpURL(String pfpURL);
    void setPermissions(ArrayList<String> permissions);
    void setPosts(ArrayList<Post> posts);

    // Methods for managing friends and profile
    void addFriend(User friend);
    void removeFriend(User friend);
    void Block(User toBeBlocked);
    boolean isBlockedBy(User blockedUser);
    boolean isFriendOf(User friend);

}
