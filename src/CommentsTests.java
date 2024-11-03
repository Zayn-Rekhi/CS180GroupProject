import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CommentsTests implements CommentsTestsInterface {

    private User user;
    private User otherUser;
    private Post post;
    private Comment comment;

    @BeforeEach
    public void setUp() {
        user = new User("testUser", "password123", "");
        otherUser = new User("testUser2", "password123", "");
        post = new Post(user, "Nice Day", "It was a great day", "11-02-2024");
        comment = new Comment(user, post, "Had so much fun", "11-02-2024");
    }

    @Test
    public void testAddLikeAndDisLikeComment() {
        for (int i = 0; i < 100; i++)
            comment.addLike();

        for (int i = 0; i < 100; i++)
            comment.addDislike();

        assertEquals(comment.getLikes(), 100);
        assertEquals(comment.getDislikes(), 100);

    }

    @Test
    public void testGetterAndSetter() {
        assertEquals(user, comment.getCommenter());
        assertEquals(2, comment.getCommentID());
        assertEquals("Had so much fun", comment.getMessage());
        assertEquals("11-02-2024", comment.getDate());
        assertEquals(0, comment.getLikes());
        assertEquals(0, comment.getDislikes());
        assertNull(comment.getEditDate());
        assertFalse(comment.isEdited());
    }

    @Test
    public void testEditMessage() {
        boolean out;
        out = comment.editMessage("Love the outfit", "11032024");

        assertFalse(out);

        out = comment.editMessage("Love the outfit", "11-03-2024");

        assertTrue(out);
        assertEquals(out, comment.isEdited());
        assertEquals("Love the outfit", comment.getMessage());
        assertEquals("11-03-2024", comment.getEditDate());
        assertEquals("11-02-2024", comment.getDate());

    }

    @Test
    public void testDisplayedComment() {
        String displayedComment = comment.displayedComment();
        String out = "testUser\n" +
                "\"Had so much fun\"\n" +
                "Date: 11-02-2024\n" +
                "Likes: 0 | Dislikes: 0";
        assertEquals(out, displayedComment);
    }

    @Test
    public void testDeleteCommentbyCommenter() {
        post.addComment(comment);
        comment.deleteComment(user, post, comment);
        assertFalse(post.getComments().contains(comment), "Comment can be deleted by the commenter.");
    }
    @Test
    public void testDeleteCommentByPostOwner() {
        post.addComment(comment);
        comment.deleteComment(post.getUser(), post, comment);
        assertFalse(post.getComments().contains(comment), "Comment can be deleted by the post owner.");
    }

    @Test
    public void testDeleteCommentByUnauthorizedUser() {
        post.addComment(comment);
        comment.deleteComment(otherUser, post, comment);
        assertTrue(post.getComments().contains(comment), "The comment should not be deleted by an unauthorized user.");
    }
}