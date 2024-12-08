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
 */
public class UserTests implements UserTestsInterface {

    private User user;
    private User friend1;
    private User friend2;

    @BeforeEach
    public void setUp() {
        user = new User("testUser", "password123", "");
        friend1 = new User("friend1", "password456", "");
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
    public void testFriendsFeed() {
        ArrayList<Post> posts1 = new ArrayList<>();
        Post friend1Post1 = new Post(friend1, "Post1", "Doing everything", "11-02-2024");
        friend1Post1.addLike(user); // 1 like
        friend1Post1.addLike(friend2); // 2 likes

        Post friend1Post2 = new Post(friend1, "Post2", "Doing everything", "11-02-2024");
        friend1Post2.addLike(user); // 1 like

        posts1.add(friend1Post1);
        posts1.add(friend1Post2);
        friend1.setPosts(posts1);

        ArrayList<Post> posts2 = new ArrayList<>();
        Post friend2Post1 = new Post(friend2, "Post3", "Doing everything", "11-02-2024");
        friend2Post1.addLike(user); // 1 like
        friend2Post1.addLike(friend1); // 2 likes

        Post friend2Post2 = new Post(friend2, "Post4", "Doing everything", "11-02-2024");

        posts2.add(friend2Post1);
        posts2.add(friend2Post2);
        friend2.setPosts(posts2);
        user.addFriend(friend1);
        user.addFriend(friend2);

        ArrayList<Post> friendsPosts = user.getFriendsFeed();

        assertEquals(friend1Post1.getTitle(), friendsPosts.get(0).getTitle());
        assertEquals(friend2Post1.getTitle(), friendsPosts.get(1).getTitle());
        assertEquals(friend1Post2.getTitle(), friendsPosts.get(2).getTitle());
        assertEquals(friend2Post2.getTitle(), friendsPosts.get(3).getTitle());

        // test total number of posts
        assertEquals(4, friendsPosts.size());
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