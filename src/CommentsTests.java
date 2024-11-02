import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CommentsTests {

    private User user;
    private Comment comment;

    @BeforeEach
    public void setUp() {
        User.setUserIDCounter(0);
        try {
            user = new User("testUser", "password123", "");
            comment = new Comment(user, "Had so much fun", "11-02-2024");
        } catch (UserCredentialsException | DateFormatException e) {
            e.printStackTrace();
        }
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
        assertEquals(1, comment.getCommentID());
        assertEquals("Had so much fun", comment.getMessage());
        assertEquals("11-02-2024", comment.getDate());
        assertEquals(0, comment.getLikes());
        assertEquals(0, comment.getDislikes());
        assertEquals(null, comment.getEditDate());
        assertFalse(comment.isEdited());
    }

    @Test
    public void testEditMessage() {
        boolean out = false;

        try {
            out = comment.editMessage("Love the outfit", "11-03-2024");
        } catch (DateFormatException e) {
            e.printStackTrace();
        }

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

}