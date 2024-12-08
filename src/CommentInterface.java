import java.util.ArrayList;

/**
 * Comment Interface
 * contains all the methods to be implemented in Comment class
 *
 * @author zaynrekhi
 * @author melody
 * @author srimadur
 * @author braydenbrafford
 * @author nothanlee
 * @version 1.0.0
 */
public interface CommentInterface {

    //accessor methods
    User getCommenter();

    Post getPost();

    int getCommentID();

    String getMessage();

    String getDate();

    int getLikes();

    boolean isEdited();

    boolean canDelete(User user);

    String getEditDate();

    ArrayList<User> getLikedUsers();

    void setLikedUsers(ArrayList<User> likedUsers);

    //mutator methods
    void deleteComment(User user); //move to post class

    void addLike(User user);

    void removeLike(User user);

    boolean editMessage(String newMessage, String editDate);

    //display method
    String displayedComment();

}