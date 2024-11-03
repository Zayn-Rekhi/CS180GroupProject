import java.io.Serializable;
import java.util.ArrayList;
/**
 * Post Class
 * This class allows Users to create a post on the platform that can have an image and/or text description.
 * Each of these posts can be liked, disliked, or commented on by other users, all of these are stored in fields.
 * This class provides functionality creating, editing, liking, disliking,
 * and commenting on posts along with displaying the post itself.
 * It implements the PostInterface and is serializable, allowing it to be saved and transferred.
 *
 * @author braydenbrafford
 *
 * @version 1.0.0
 */
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
        try {
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

            if (!checkDate(date)) {
                throw new DateFormatException("Date is incorrectly formatted! Make sure it is 00/00/0000 (Month/Day/Year)");
            }
        } catch (DateFormatException e) {
            e.printStackTrace();
        }

    }

    public Post(User user, String title, String description, String date) {
        try {
            this.user = user;
            this.title = title;
            this.image = false;
            this.description = description;
            this.comments = new ArrayList<>();
            this.likes = 0;
            this.dislikes = 0;
            this.date = date;

            if (!checkDate(date)) {
                throw new DateFormatException("Date is incorrectly formatted! Make sure it is 00/00/0000 (Month/Day/Year)");
            }
        } catch (DateFormatException e) {
            e.printStackTrace();
        }

    }

    public static boolean checkDate(String date) {
        try {
            String[] split = date.split("-");

            String month = split[0];
            String day = split[1];
            String year = split[2];

            if (!(Integer.parseInt(month) < 13 && Integer.parseInt(month) > 0)) {
                return false;
            }

            if (!(Integer.parseInt(day) < 32 && Integer.parseInt(day) > 0)) {
                return false;
            }

            return year.length() == 4;
        } catch (Exception e) {
            return false;
        }
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