import java.io.Serializable;

public class Comment implements CommentInterface,Serializable {

    // fields
    private User commenter;
    private String message;
    private String date;
    private int likes;
    private int dislikes;
    private boolean edited;
    private String editDate;
    private int commentID;
    private static int commentIDCounter = 0;

    // constructors
    public Comment(User commenter, String message, String date) throws DateFormatException {
        commentID = commentIDCounter;
        commentIDCounter++;
        this.message = message;
        this.date = date;
        this.likes = 0;
        this.dislikes = 0;
        this.edited = false;
        this.commenter = commenter;

        if (!Post.checkDate(date)) {
            throw new DateFormatException("Date is incorrectly formatted! Make sure it is 00/00/0000 (Month/Day/Year)");
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
    public void addLike() {
        likes++;
    }
    public void addDislike() {
        dislikes++;
    }
    public void editMessage(String newMessage, String editDate) {
        message = newMessage;
        this.editDate = editDate;
        edited = true;
    }

    public String displayedComment() {
        if (edited) {
            return String.format("%s\n\"%s\"\n*edited\nDate: %s | Edit Date: %s\nLikes: %d | Dislikes: %d", commenter.getUserName(), message, date, editDate, likes, dislikes);
        } else {
            return String.format("%s\n\"%s\"\nDate: %s\nLikes: %d | Dislikes: %d", commenter.getUserName(), message, date, likes, dislikes);
        }
    }



}