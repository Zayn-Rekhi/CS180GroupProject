public interface CommentInterface {

    //accessor methods
    User getCommenter();
    int getCommentID();
    String getMessage();
    String getDate();
    int getLikes();
    int getDislikes();
    boolean isEdited();
    boolean canDelete(User user);
    String getEditDate();

    //mutator methods
    void deleteComment(User user, Post post, Comment comment); //move to post class
    void addLike();
    void addDislike();
    boolean editMessage(String newMessage, String editDate);

    //display method
    String displayedComment();

}