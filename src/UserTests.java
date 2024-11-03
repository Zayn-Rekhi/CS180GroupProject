import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;

/**
 * User Test Class
 * This class tests out all the methods in the User class to make sure that they are running smoothly with edge
 * cases. It ensures that the way in which comments, posts, and users are stringed together doesn't give rise to
 * any errors. More specifically, we test functionality such as initialization for Users,
 * their ability to edit their attributes, their interactions with friends and blocked users, their permissions,
 * their feed, and their posting.
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
public class UserTests implements UserTestsInterface {

    private User user;
    private User friend1;
    private User friend2;

    @BeforeEach
    public void setUp() {
        user = new User("testUser", "password123", "");
        friend1 = new User( "friend1", "password456", "");
        friend2 = new User("friend2", "password789", "");
    }

    @Test
    public void testUserInitialization() {
        // Supposed to be six since it 6 users are initialized before this
        // By the setUp Method
        assertEquals(6, user.getUserID());
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
    public void testUnBlockFriend() {
        user.block(friend2);
        assertTrue(user.getBlocked().contains(friend2));

        user.unBlock(friend2);
        assertFalse(user.getBlocked().contains(friend2));
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
    public void testFriendsFeed() {
        ArrayList<Post> posts1 = new ArrayList<>();
        posts1.add(new Post(friend1, "Post1",  "Doing everything", "11-02-2024"));
        posts1.add(new Post(friend1, "Post2", "Doing everything", "11-02-2024"));
        friend1.setPosts(posts1);

        ArrayList<Post> posts2 = new ArrayList<>();
        posts2.add(new Post(friend2, "Post1",  "Doing everything", "11-02-2024"));
        posts2.add(new Post(friend2, "Post2",  "Doing everything", "11-02-2024"));
        friend2.setPosts(posts2);

        user.addFriend(friend1);
        user.addFriend(friend2);

        ArrayList<Post> friendsPosts = this.user.getFriendsFeed();

        assertEquals(posts1.get(0), friendsPosts.get(0));
        assertEquals(posts1.get(1), friendsPosts.get(1));
        assertEquals(posts2.get(0), friendsPosts.get(2));
        assertEquals(posts2.get(1), friendsPosts.get(3));
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

        Post n = new Post(user, "Post3", "Great Day", "Doing everything", "11-02-2024");
        user.post(n);

        assertEquals(n, user.getPosts().get(2));
    }
}