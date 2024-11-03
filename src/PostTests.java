import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.zip.DataFormatException;

public class PostTests {
    @Test
    public void testPostClass() {
        // create a User and a Post object
        User user = new User("testUser", "password", "This is a bio.");
        Post post = new Post(user, "Test Title", "http://example.com/image.jpg", "Test description.", "11-02-2024");



        // test the Post's initial values using accessor methods
        assertEquals("Test Title", post.getTitle());
        assertEquals("http://example.com/image.jpg", post.getImageURL());
        assertEquals("Test description.", post.getDescription());
        assertEquals(0, post.getLikes());
        assertEquals(0, post.getDislikes());
        assertEquals(user, post.getUser());

        // test post without image constructor
        Post postWithoutImage = new Post(user, "Title without Image", "Description", "11-02-2024");
        assertFalse(postWithoutImage.hasImage());

        // test liking the post
        post.addLike();
        assertEquals(1, post.getLikes());

        // test disliking the post
        post.addDislike();
        assertEquals(1, post.getDislikes());

        // test the displayedPost method
        String expectedDisplay = "testUser\nTest Title\nhttp://example.com/image.jpg\nTest description.\nDate: 11-02-2024\nLikes: 1 | Dislikes: 1";
        assertEquals(expectedDisplay, post.displayedPost().trim());

        // test the functionality of adding a comment
        Comment comment = new Comment(user, post, "Great post!", "11-02-2024");
        post.addComment(comment);
        assertEquals(1, post.getComments().size());
        assertEquals(comment, post.getComments().get(0));

        // test the displayedPost method after adding a comment
        String expectedDisplayWithComment = "testUser\nTest Title\nhttp://example.com/image.jpg\nTest description.\nDate: 11-02-2024\nLikes: 1 | Dislikes: 1\n" + comment.displayedComment();
        assertEquals(expectedDisplayWithComment, post.displayedPost().trim());

        // test removing a comment
        boolean commentRemoved = post.removeComment(comment);
        assertTrue(commentRemoved);
        assertEquals(0, post.getComments().size());
    }
}