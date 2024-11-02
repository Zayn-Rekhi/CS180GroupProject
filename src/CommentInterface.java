public interface CommentInterface {

    //accessor methods
    User getCommenter();
    int getCommentID();
    String getMessage();
    String getDate();
    int getLikes();
    int getDislikes();
    boolean isEdited();
    String getEditDate();

    //mutator methods
    void addLike();
    void addDislike();
    boolean editMessage(String newMessage, String editDate);

    //display method
    String displayedComment();

}