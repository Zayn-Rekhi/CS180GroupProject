import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;

public class UserTests {

    private User user;
    private User friend1;
    private User friend2;

    @BeforeEach
    public void setUp() {
        User.setUserIDCounter(0);
        try {
            user = new User("testUser", "password123", "");
            friend1 = new User( "friend1", "password456", "");
            friend2 = new User("friend2", "password789", "");
        } catch (UserCredentialsException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testUserInitialization() {
        assertEquals(0, user.getUserID());
        assertEquals("testUser", user.getUserName());
        assertEquals("password123", user.getPassword());
        assertEquals("", user.getBio());
        assertEquals("", user.getPfpURL());
        assertTrue(user.getPermissions().isEmpty());
        assertTrue(user.getPosts().isEmpty());
        assertTrue(user.getFriends().isEmpty());
        assertTrue(user.getBlocked().isEmpty());
    }

    @Test
    public void testSettersAndGetters() {
        user.setUserName("newUserName");
        user.setPassword("newPassword");
        user.setBio("New bio");
        user.setPfpURL("https://example.com/pfp.jpg");

        assertEquals("newUserName", user.getUserName());
        assertEquals("newPassword", user.getPassword());
        assertEquals("New bio", user.getBio());
        assertEquals("https://example.com/pfp.jpg", user.getPfpURL());
    }

    @Test
    public void testAddFriend() {
        user.addFriend(friend1);
        assertTrue(user.getFriends().contains(friend1));

        user.addFriend(friend1);
        assertEquals(1, user.getFriends().size());
    }

    @Test
    public void testRemoveFriend() {
        user.addFriend(friend1);
        user.removeFriend(friend1);
        assertFalse(user.getFriends().contains(friend1));
    }

    @Test
    public void testBlockFriend() {
        user.block(friend2);
        assertTrue(user.getBlocked().contains(friend2));

        // Test blocking the same user again - should not duplicate
        user.block(friend2);
        assertEquals(1, user.getBlocked().size());
    }

    @Test
    public void testUpdateProfile() {
        String newBio = "This is a new bio";
        user.setBio(newBio);
        assertEquals(newBio, user.getBio());
    }

    @Test
    public void testPermissions() {
        ArrayList<String> permissions = new ArrayList<>();
        permissions.add("READ");
        permissions.add("WRITE");
        user.setPermissions(permissions);

        assertEquals(permissions, user.getPermissions());
        assertEquals("READ", user.getPermissions().get(0));
        assertEquals("WRITE", user.getPermissions().get(1));
    }

    @Test
    public void testPosts() {
        ArrayList<Post> posts = new ArrayList<>();
        posts.add(new Post(user, "Post1", "Great Day", "Doing everything", "11-02-2024"));
        posts.add(new Post(user, "Post2", "Great Day", "Doing everything", "11-02-2024"));
        user.setPosts(posts);

        assertEquals(posts, user.getPosts());
        assertEquals("Post1", user.getPosts().get(0).getTitle());
        assertEquals("Post2", user.getPosts().get(1).getTitle());
    }
}