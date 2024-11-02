import java.util.ArrayList;

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

    //mutator methods
    void setUser(User user);
    void setTitle(String title);
    void setImageURL(String imageURL);
    void setDescription(String description);

    //managing likes and dislikes
    void addLike();
    void removeLike();
    void addDislike();
    void removeDislike();

    //managing comments
    void addComment(Comment comment);
    boolean removeComment(Comment comment);

    //display post method
    String displayedPost();
}