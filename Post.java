import java.util.ArrayList;
import java.io.Serializable;
public class Post implements PostInterface, Serializable {

    //instance variables
    private ArrayList<Comment> comments;
    private String title;
    private boolean image;
    private String imageURL;
    private String description;
    private String date;
    private int likes;
    private User user;
    private int dislikes;
    private int postID;
    private static int postIDCounter = 0;

    //constructors
    public Post(User user, String title, String imageURL, String description) {
        postID = postIDCounter;
        postIDCounter++;
        this.user = user;
        this.title = title;
        this.image = true;
        this.imageURL = imageURL;
        this.description = description;
        this.comments = new ArrayList<>();
        this.likes = 0;
        this.dislikes = 0;
    }
    
    public Post(User user, String title, String description) {
        this.user = user;
        this.title = title;
        this.image = false;
        this.description = description;
        this.comments = new ArrayList<>();
        this.likes = 0;
        this.dislikes = 0;
    }


    //getters
    public User getUser() {
        return user;
    }

    public int getPostID() {
        return postID;
    }

    public String getTitle() {
        return title;
    }

    public boolean hasImage() {
        return image;
    }

    public String getImageURL() {
        return imageURL;
    }

    public String getDescription() {
        return description;
    }

    public int getLikes() {
        return likes;
    }

    public int getDislikes() {
        return dislikes;
    }

    //setters
    public void setUser(User user) {
        this.user = user;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
        this.image = true;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void addLike() {
        this.likes++;
    }

    public void removeLike() {
        this.likes--;
    }

    public void addDislike() {
        this.dislikes++;
    }

    public void removeDislike() {
        this.dislikes--;
    }

    public void addComment(Comment comment) {
        this.comments.add(comment);
    }

    public boolean removeComment(Comment comment) {
        if (comments.contains(comment)) {
            comments.remove(comment);
            return true;
        }
        return false;
    }

    //toString
    public String displayedPost() {
        String returnValue = "";
        returnValue = user.getUserName() + "\n" + this.title + "\n";
        if (image) {
            returnValue += this.imageURL + "\n";
        }
        returnValue += this.description + "\n";
        returnValue += "Date: " + this.date + "\n";
        returnValue += "Likes: " + this.likes + " | " + "Dislikes: " + this.dislikes;
        for (Comment c : comments) {
            returnValue += c.displayedComment();
        }
        return returnValue;
    }
    

}