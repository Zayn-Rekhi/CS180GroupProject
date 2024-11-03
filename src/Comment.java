import java.io.Serializable;
/**
 * TPJ -- Comment
 *
 * this describes an exception for the stock class due to invalid input
 *
 * @author Brayden Brafford, Zayn Rekhi, Srinidhi Madur, Nathan Lee, Melody Yang, 8
 *
 * @version Nov 3, 2024
 *
 */
public class Comment implements CommentInterface, Serializable {

    // fields
    private User commenter;
    private Post post;
    private String message;
    private String date;
    private int likes;
    private int dislikes;
    private boolean edited;
    private String editDate;
    private int commentID;
    private static int commentIDCounter = 0;

    // constructor
    public Comment(User commenter, Post post, String message, String date) {
        try {
            commentID = commentIDCounter;
            commentIDCounter++;
            this.message = message;
            this.date = date;
            this.likes = 0;
            this.dislikes = 0;
            this.edited = false;
            this.commenter = commenter;
            this.post = post;

            if (!Post.checkDate(date)) {
                throw new DateFormatException("Date is incorrectly formatted! Make sure it is 00/00/0000 (Month/Day/Year)");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //accessor methods
    public User getCommenter() {
        return commenter;
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
    public int getDislikes() {
        return dislikes;
    }
    public boolean isEdited() {
        return edited;
    }
    public String getEditDate() {
        return editDate;
    }

    //mutator methods

    //deletes comment
    public void deleteComment(User user, Post post, Comment comment) {
        if (canDelete(user)) {
            System.out.println("Trying to delete");
            post.getComments().remove(comment);
        } else {
            System.out.println("User is not authorized to delete this comment.");
        }
    }
    //adds a like to the comment
    public void addLike() {
        likes++;
    }
    //adds a dislike to the comment
    public void addDislike() {
        dislikes++;
    }
    //edits the message in the comment with a new message
    public boolean editMessage(String newMessage, String editDate) {
        try {
            if (!Post.checkDate(editDate)) {
                throw new DateFormatException("Date is incorrectly formatted! Make sure it is 00/00/0000 (Month/Day/Year)");
            }
            message = newMessage;
            this.editDate = editDate;
            edited = true;
            return true;
        } catch (DateFormatException e) {
            return false;
        }

    }

    //other methods

    //checks to make sure the user can delete the comment, (if they either own the post or the comment)
    public boolean canDelete(User user) {
        return user.equals(commenter) || user.equals(post.getUser());
    }
    //displays the comment in an organized format as a string
    public String displayedComment() {
        if (edited) {
            return String.format("%s\n\"%s\"\n*edited\nDate: %s | Edit Date: %s\nLikes: %d | Dislikes: %d", commenter.getUserName(), message, date, editDate, likes, dislikes);
        } else {
            return String.format("%s\n\"%s\"\nDate: %s\nLikes: %d | Dislikes: %d", commenter.getUserName(), message, date, likes, dislikes);
        }
    }
}
