import java.util.ArrayList;

public interface UserInterface {

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

    // Setters
    void setUserName(String userName);
    void setPassword(String password);
    void setBio(String bio);
    void setPfpURL(String pfpURL);
    void setPermissions(ArrayList<String> permissions);
    void setPosts(ArrayList<Post> posts);
    void setFriends(ArrayList<User> friends);
    void setBlocked(ArrayList<User> blocked);

    // Methods for managing friends and profile
    void addFriend(User friend);
    void removeFriend(User friend);
    void blockFriend(User friend);
    void updateProfile(String bio);
}
