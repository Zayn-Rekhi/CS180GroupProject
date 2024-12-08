import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Post Test Class
 * This class tests out all the methods in the Post class to make sure that they are running smoothly with edge
 * cases. It ensures that the way in which comments, posts, and users are stringed together doesn't give rise to
 * any errors. More specifically, we test functionality such as initialization for both posts with and without images,
 * comments and likes, getters and setters, displaying posts wit comments, and delete comments by authorized users.
 *
 * @author zaynrekhi
 * @author melody
 * @author srimadur
 * @author braydenbrafford
 * @author nothanlee
 * @version 1.0.0
 *
 * @version 1.0.0
 */

public class PostTests implements PostTestsInterface {
    private User user;
    private Post post;

    @BeforeEach
    public void setUp() {
        user = new User("testUser", "password", "This is a bio.");
        post = new Post(user, "Test Title", "http://example.com/image.jpg", "Test description.", "11-02-2024");
    }

    @Test
    public void testInitialization() {
        // test the Post's initial values using accessor methods
        assertEquals(4, post.getPostID());
        assertEquals("Test Title", post.getTitle());
        assertEquals("http://example.com/image.jpg", post.getImageURL());
        assertEquals("Test description.", post.getDescription());
        assertEquals(0, post.getLikes());
        assertEquals(0, post.getDislikes());
        assertEquals(user, post.getUser());
    }

    @Test
    public void testMutators() {
        User u = new User("testUser", "password", "This is a bio.");
        String title = "Amazing day outside!";
        String imageURL = "http://example.com/image.jpg";
        String description = "Went to New York City!";

        post.setUser(u);
        post.setTitle(title);
        post.setImageURL(imageURL);
        post.setDescription(description);

        assertEquals(title, post.getTitle());
        assertEquals(imageURL, post.getImageURL());
        assertEquals(description, post.getDescription());
        assertEquals(user, post.getUser());
    }

    @Test
    public void testPostWithoutIMGConstructor() {
        // test post without image constructor
        Post postWithoutImage = new Post(user, "Title without Image", "Description", "11-02-2024");
        assertFalse(postWithoutImage.hasImage());
    }

    @Test
    public void testAddAndRemoveLike() {
        // test post without image constructor
        for (int i = 0; i < 100; i++)
            post.addLike(user);

        for (int i = 0; i < 100; i++)
            post.removeLike(user);

        assertEquals(0, post.getLikes());
    }

    @Test
    public void testAddAndRemoveDislike() {
        // test disliking the post
        for (int i = 0; i < 100; i++)
            post.addDislike();

        for (int i = 0; i < 100; i++)
            post.removeDislike();

        assertEquals(0, post.getDislikes());
    }

    @Test
    public void testDisplay() {
        // test the displayedPost method
        String expectedDisplay = "testUser\nTest Title\nhttp://example.com/image.jpg\nTest " +
                "description.\nDate: 11-02-2024\nLikes: 0 | Dislikes: 0";
        assertEquals(expectedDisplay, post.displayedPost().trim());
    }

    @Test
    public void testComment() {
        // test the functionality of adding a comment
        Comment comment = new Comment(user, post, "Great post!", "11-02-2024");
        post.addComment(comment);
        assertEquals(1, post.getComments().size());
        assertEquals(comment, post.getComments().get(0));
    }


    @Test
    public void testDisplayWithComment() {
        Comment comment = new Comment(user, post, "Great post!", "11-02-2024");
        post.addComment(comment);
        // test the displayedPost method after adding a comment
        String expectedDisplayWithComment = "testUser\nTest Title\nhttp://example.com/image.jpg\nTest " +
                "description.\nDate: 11-02-2024\nLikes: 0 | Dislikes: 0\n" + comment.displayedComment();
        assertEquals(expectedDisplayWithComment, post.displayedPost().trim());
    }

    @Test
    public void testRemoveComment() {
        // test removing a comment
        Comment comment = new Comment(user, post, "Great post!", "11-02-2024");
        post.addComment(comment);
        boolean commentRemoved = post.removeComment(comment);
        assertTrue(commentRemoved);
        assertEquals(0, post.getComments().size());
    }
}