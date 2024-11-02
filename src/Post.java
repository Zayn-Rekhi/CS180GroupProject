package src;

import java.io.Serializable;
import java.util.ArrayList;
public class Post implements PostInterface, Serializable {

    // instance variables
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

    // constructors
    public Post(User user, String title, String imageURL, String description, String date) {
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
        this.date = date;
    }
    
    public Post(User user, String title, String description, String date) {
        this.user = user;
        this.title = title;
        this.image = false;
        this.description = description;
        this.comments = new ArrayList<>();
        this.likes = 0;
        this.dislikes = 0;
        this.date = date;
    }


    // accessor methods
    public ArrayList<Comment> getComments() {
        return comments;
    }
    
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

    // mutator methods
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

    // managing likes and dislikes methods
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

    // managing comments methods
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

    // display post method
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
            returnValue += "\n" + c.displayedComment();
        }
        return returnValue;
    }
}