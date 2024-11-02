package src;

public interface PostInterface {
    User getUser();
    int getPostID();
    String getTitle();
    boolean hasImage();
    String getImageURL();
    String getDescription();
    int getLikes();
    int getDislikes();
    
    void setUser(User user);
    void setTitle(String title);
    void setImageURL(String imageURL);
    void setDescription(String description);
    void addLike();
    void removeLike();
    void addDislike();
    void removeDislike();
    void addComment(Comment comment);
    boolean removeComment(Comment comment);
    
    String displayedPost();
}