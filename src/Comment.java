import java.io.Serializable;
import java.util.ArrayList;

/**
 * Comment Class
 * This class allows Users to create a comment that can be liked, disliked,
 * and have a message. Each comment has a user and a post that it is stored in
 * along with a user and a post parameter.  This class provides functionality for creating, editing, liking,
 * disliking, and deleting comments, as well as displaying information about the comment.
 * It implements the CommentInterface and is serializable, allowing it to be easily saved or transferred.
 *
 * @author zaynrekhi
 * @author melody
 * @author srimadur
 * @author braydenbrafford
 * @author nothanlee
 * @version 1.0.0
 */
public class Comment implements CommentInterface, Serializable {

    // fields
    private User commenter;
    private ArrayList<User> liked;
    private Post post;
    private String message;
    private String date;
    private int likes;
    private boolean edited;
    private String editDate;
    private int commentID;
    private static int commentIDCounter = 0;

    // constructors
    public Comment(User commenter, Post post, String message, String date) {
        try {
            commentID = commentIDCounter;
            commentIDCounter++;
            this.message = message;
            this.date = date;
            this.likes = 0;
            this.edited = false;
            this.commenter = commenter;
            this.post = post;
            this.liked = new ArrayList<>();

            if (!Post.checkDate(date)) {
                String msg = "Date is incorrectly formatted! Make sure it is 00/00/0000 (Month/Day/Year)";
                throw new DateFormatException(msg);
            }
        } catch (DateFormatException e) {
            e.printStackTrace();
        }

    }

    //accessor methods
    public User getCommenter() {
        return commenter;
    }

    public Post getPost() {
        return post;
    }

    public int getCommentID() {
        return commentID;
    }

    public String getMessage() {
        return message;
    }

    public String getDate() {
        return date;
    }

    public int getLikes() {
        return likes;
    }

    public boolean isEdited() {
        return edited;
    }

    public String getEditDate() {
        return editDate;
    }

    //mutator methods
    public boolean canDelete(User user) {
        return user.equals(commenter) || user.equals(this.post.getUser());
    }

    public void setCommenter(User commenter) {
        this.commenter = commenter;
    }

    public void deleteComment(User user) {
        if (canDelete(user)) {
            System.out.println("Comment deleted");
            post.getComments().remove(this);
        }
    }

    // managing likes and dislikes methods
    public void addLike(User user) {
        if (!checkLiked(user)) {
            this.likes++;
            liked.add(user);
        }
    }

    public void removeLike(User user) {
        if (checkLiked(user)) {
            this.likes--;
            liked.remove(user);
        }
    }

    public boolean checkLiked(User user) {
        return liked.contains(user);
    }

    public ArrayList<User> getLikedUsers() {
        return liked;
    }

    public void setLikedUsers(ArrayList<User> likedUsers) {
        this.liked = likedUsers;
    }

    public boolean editMessage(String newMessage, String newDate) {
        try {
            if (!Post.checkDate(newDate)) {
                String msg = "Date is incorrectly formatted! Make sure it is 00/00/0000 (Month/Day/Year)";
                throw new DateFormatException(msg);
            }
            message = newMessage;
            this.editDate = newDate;
            edited = true;
            return true;
        } catch (DateFormatException e) {
            return false;
        }

    }

    public String displayedComment() {
        if (edited) {
            String fStr = "%s\n\"%s\"\n*edited\nDate: %s | Edit Date: %s\nLikes: %d";
            return String.format(fStr, commenter.getUserName(), message, date, editDate, likes);
        } else {
            String fStr = "%s\n\"%s\"\nDate: %s\nLikes: %d";
            return String.format(fStr, commenter.getUserName(), message, date, likes);
        }
    }
}