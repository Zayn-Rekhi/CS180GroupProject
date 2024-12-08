import java.util.ArrayList;

/**
 * Post Interface
 * contains all the methods to be implemented in the Post class
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

public interface PostInterface {
    //accessor methods
    ArrayList<Comment> getComments();
    User getUser();
    int getPostID();
    String getTitle();
    boolean hasImage();
    String getImageURL();
    String getDescription();
    int getLikes();
    int getDislikes();
    String getDate();
    ArrayList<User> getLikedUsers();
    void setLikedUsers(ArrayList<User> likedUsers);

    //mutator methods
    void setUser(User user);
    void setTitle(String title);
    void setImageURL(String imageURL);
    void setDescription(String description);

    //managing likes and dislikes
    void addLike(User user);
    void removeLike(User user);
    void addDislike();
    void removeDislike();

    //managing comments
    void addComment(Comment comment);
    boolean removeComment(Comment comment);

    //display post method
    String displayedPost();
}