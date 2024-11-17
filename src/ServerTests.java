import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;

/**
 * Server Test Class
 * This class tests out all the methods in the Server class to make sure that they are running smoothly with edge
 * cases. It ensures that the way the Server processes commands to change the database all works as intended.
 * We test every possible command the server needs to handle, and make sure the database is updated correctly.
 *
 * @author zaynrekhi
 * @author melody
 * @author srimadur
 * @author braydenbrafford
 * @author nothanlee
 * @version 1.0.0
 *
 */
public class ServerTests implements ServerTestsInterface {
    private Server server;
    private Database database;

    @BeforeEach
    public void setUp() {
        database = new Database();
        database.clear();

        server = new Server(null);

        this.createUserBrandonExample();
        this.createUserSriExample();
        this.createUserZaynExample();
    }


    public void createUserZaynExample() {
        ArrayList<String> credentials = new ArrayList<>();
        credentials.add("ZaynRekhi123");
        credentials.add("zayn11!23");
        credentials.add("Zayn description");

        DataTransfer dt = new DataTransfer("USER CREATENEWUSER", credentials);
        DataTransfer out = server.processCommands(dt);

        assertEquals(out.getMessage(), "SUCCESS"); // Request went through

        ArrayList<Object> postInfo = new ArrayList<>();
        postInfo.add(out.getValue());
        postInfo.add("A great day out!");
        postInfo.add("https://example.com/pfp.jpg");
        postInfo.add("Post Description");
        postInfo.add("11-02-2024");

        DataTransfer dtp = new DataTransfer("POST CREATEPOST", postInfo);
        DataTransfer outPost = server.processCommands(dtp); // Request went through

        assertEquals(outPost.getMessage(), "SUCCESS");

        ArrayList<Object> commentInfo = new ArrayList<>();
        commentInfo.add(out.getValue());
        commentInfo.add(outPost.getValue());
        commentInfo.add("Great Comment!");
        commentInfo.add("11-02-2024");

        DataTransfer dtc = new DataTransfer("COMMENT CREATECOMMENT", commentInfo);
        DataTransfer outComment = server.processCommands(dtc); // Request went through

        assertEquals(outComment.getMessage(), "SUCCESS");
    }


    public void createUserSriExample() {
        ArrayList<String> credentials = new ArrayList<>();
        credentials.add("SriMadur");
        credentials.add("sri123");
        credentials.add("goinggreat");

        DataTransfer dt = new DataTransfer("USER CREATENEWUSER", credentials);
        DataTransfer out = server.processCommands(dt);

        assertEquals(out.getMessage(), "SUCCESS"); // Request went through
        assertNotNull(database.findUser(credentials.get(0))); // Finds User in Database

        ArrayList<Object> postInfo = new ArrayList<>();
        postInfo.add(out.getValue());
        postInfo.add("A great day out!");
        postInfo.add("https://example.com/pfp.jpg");
        postInfo.add("Post Description");
        postInfo.add("11-02-2024");

        DataTransfer dtp = new DataTransfer("POST CREATEPOST", postInfo);
        DataTransfer outPost = server.processCommands(dtp); // Request went through

        assertEquals(outPost.getMessage(), "SUCCESS");

        ArrayList<Object> commentInfo = new ArrayList<>();
        commentInfo.add(out.getValue());
        commentInfo.add(outPost.getValue());
        commentInfo.add("Great Comment!");
        commentInfo.add("11-02-2024");

        DataTransfer dtc = new DataTransfer("COMMENT CREATECOMMENT", commentInfo);
        DataTransfer outComment = server.processCommands(dtc); // Request went through

        assertEquals(outComment.getMessage(), "SUCCESS");
    }



    public void createUserBrandonExample() {
        ArrayList<String> credentials = new ArrayList<>();
        credentials.add("brandon");
        credentials.add("123123123");
        credentials.add("amazing");

        DataTransfer dt = new DataTransfer("USER CREATENEWUSER", credentials);
        DataTransfer out = server.processCommands(dt);

        assertEquals(out.getMessage(), "SUCCESS"); // Request went through
        assertNotNull(database.findUser(credentials.get(0))); // Finds User in Database

        ArrayList<Object> postInfo = new ArrayList<>();
        postInfo.add(out.getValue());
        postInfo.add("A great day out!");
        postInfo.add("https://example.com/pfp.jpg");
        postInfo.add("Post Description");
        postInfo.add("11-02-2024");

        DataTransfer dtp = new DataTransfer("POST CREATEPOST", postInfo);
        DataTransfer outPost = server.processCommands(dtp); // Request went through

        assertEquals(outPost.getMessage(), "SUCCESS");

        ArrayList<Object> commentInfo = new ArrayList<>();
        commentInfo.add(out.getValue());
        commentInfo.add(outPost.getValue());
        commentInfo.add("Great Comment!");
        commentInfo.add("11-02-2024");

        DataTransfer dtc = new DataTransfer("COMMENT CREATECOMMENT", commentInfo);
        DataTransfer outComment = server.processCommands(dtc); // Request went through

        assertEquals(outComment.getMessage(), "SUCCESS");
    }

    @Test
    public void testCreateUsers() {
        assertNotNull(database.findUser("ZaynRekhi123")); // Finds User in Database
        assertNotNull(database.findUser("SriMadur")); // Finds User in Database
        assertNotNull(database.findUser("brandon")); // Finds User in Database
    }

    @Test
    public void testCreateUsersInvalidCredentials() {
        ArrayList<String> credentials = new ArrayList<>();
        credentials.add("zr");
        credentials.add("zayn");
        credentials.add("Zayn is great at blah blah blah");

        DataTransfer dt = new DataTransfer("USER CREATENEWUSER", credentials);
        DataTransfer out = server.processCommands(dt);

        assertEquals(out.getMessage(), "FAILURE"); // Request went through
        assertNull(database.findUser(credentials.get(0))); // Finds User in Database
    }

    @Test
    public void testGetLogin() {
        ArrayList<String> credentials = new ArrayList<>();
        credentials.add("ZaynRekhi123");
        credentials.add("zayn11!23");

        DataTransfer dt = new DataTransfer("USER GETLOGIN", credentials);
        DataTransfer out = server.processCommands(dt);

        assertEquals(out.getMessage(), "SUCCESS"); // Request went through
        assertNotNull(database.findUser(credentials.get(0))); // Finds User in Database
    }

    @Test
    public void testRemoveUser() {
        User user = database.findUser("ZaynRekhi123");

        DataTransfer dt = new DataTransfer("USER DELETEUSER", user);
        DataTransfer out = server.processCommands(dt);

        assertEquals(out.getMessage(), "SUCCESS"); // Request went through
        assertNull(database.findUser("ZaynRekhi123"));
    }

    @Test
    public void testModifyUser() {
        User user = database.findUser("ZaynRekhi123");
        user.setBio("Zayn");

        DataTransfer dt = new DataTransfer("USER MODIFYUSER", user);
        DataTransfer out = server.processCommands(dt);

        String newBio = database.findUser("ZaynRekhi123").getBio();
        assertEquals(out.getMessage(), "SUCCESS"); // Request went through
        assertEquals("Zayn", newBio);
    }

    @Test
    public void testAddFriend() {
        User user = database.findUser("ZaynRekhi123");
        User friend = database.findUser("brandon");

        ArrayList<User> users = new ArrayList<>();
        users.add(user);
        users.add(friend);

        DataTransfer dt = new DataTransfer("USER ADDFRIEND", users);
        DataTransfer out = server.processCommands(dt);

        User f = database.findUser("ZaynRekhi123").getFriends().get(0);

        assertEquals(out.getMessage(), "SUCCESS");
        assertEquals(f.getUserName(), friend.getUserName());
    }

    @Test
    public void testRemoveFriend() {
        // Adding the Friend
        User user = database.findUser("ZaynRekhi123");
        User friend = database.findUser("brandon");

        ArrayList<User> users = new ArrayList<>();
        users.add(user);
        users.add(friend);

        DataTransfer dt = new DataTransfer("USER ADDFRIEND", users);
        DataTransfer out = server.processCommands(dt);

        User f = database.findUser("ZaynRekhi123").getFriends().get(0);

        assertEquals(out.getMessage(), "SUCCESS");
        assertEquals(f.getUserName(), friend.getUserName());

        // Removing the Friend
        user = database.findUser("ZaynRekhi123");
        friend = database.findUser("brandon");

        users = new ArrayList<>();
        users.add(user);
        users.add(friend);

        dt = new DataTransfer("USER REMOVEFRIEND", users);
        out = server.processCommands(dt);

        var friendList = database.findUser("ZaynRekhi123").getFriends();

        assertEquals(out.getMessage(), "SUCCESS");
        assertEquals(friendList.size(), 0);
    }

    @Test
    public void testBlockFriend() {
        User user = database.findUser("ZaynRekhi123");
        User friend = database.findUser("brandon");

        ArrayList<User> users = new ArrayList<>();
        users.add(user);
        users.add(friend);

        DataTransfer dt = new DataTransfer("USER BLOCKFRIEND", users);
        DataTransfer out = server.processCommands(dt);

        User f = database.findUser("ZaynRekhi123").getBlocked().get(0);

        assertEquals(out.getMessage(), "SUCCESS");
        assertEquals(f.getUserName(), friend.getUserName());
    }

    @Test
    public void testUnBlockFriend() {
        // Adding the Friend
        User user = database.findUser("ZaynRekhi123");
        User friend = database.findUser("brandon");

        ArrayList<User> users = new ArrayList<>();
        users.add(user);
        users.add(friend);

        DataTransfer dt = new DataTransfer("USER BLOCKFRIEND", users);
        DataTransfer out = server.processCommands(dt);

        User f = database.findUser("ZaynRekhi123").getBlocked().get(0);

        assertEquals(out.getMessage(), "SUCCESS");
        assertEquals(f.getUserName(), friend.getUserName());

        // Removing the Friend
        user = database.findUser("ZaynRekhi123");
        friend = database.findUser("brandon");

        users = new ArrayList<>();
        users.add(user);
        users.add(friend);

        dt = new DataTransfer("USER UNBLOCKFRIEND", users);
        out = server.processCommands(dt);

        var friendList = database.findUser("ZaynRekhi123").getBlocked();

        assertEquals(out.getMessage(), "SUCCESS");
        assertEquals(friendList.size(), 0);
    }

    @Test
    public void testCreatePost() {
        assertEquals(database.findUser("ZaynRekhi123").getPosts().size(), 1); // Finds User in Database
        assertEquals(database.findUser("SriMadur").getPosts().size(), 1); // Finds User in Database
        assertEquals(database.findUser("brandon").getPosts().size(), 1); // Finds User in Database
    }

    @Test
    public void testDeletePost() {
        User user = database.findUser("ZaynRekhi123");
        Post post = user.getPosts().get(0);

        DataTransfer dt = new DataTransfer("POST DELETEPOST", post);
        DataTransfer out = server.processCommands(dt);

        assertEquals(out.getMessage(), "SUCCESS"); // Request went through
        assertEquals(database.findUser("ZaynRekhi123").getPosts().size(), 0);
    }

    @Test
    public void testEditPost() {
        User user = database.findUser("ZaynRekhi123");
        Post post = user.getPosts().get(0);

        post.setTitle("New Title");
        post.setDescription("New Description");
        post.setImageURL("www.google.com/images.png");

        DataTransfer dt = new DataTransfer("POST EDITPOST", post);
        DataTransfer out = server.processCommands(dt);

        Post newPost = database.findUser("ZaynRekhi123").getPosts().get(0);

        assertEquals(out.getMessage(), "SUCCESS");
        assertEquals(newPost.getTitle(), "New Title");
        assertEquals(newPost.getDescription(), "New Description");
        assertEquals(newPost.getImageURL(), "www.google.com/images.png");
    }

    @Test
    public void testLikePost() {
        User user = database.findUser("ZaynRekhi123");
        Post post = user.getPosts().get(0);

        DataTransfer dt = new DataTransfer("POST LIKEPOST", post);
        DataTransfer out = server.processCommands(dt);

        assertEquals(out.getMessage(), "SUCCESS"); // Request went through
        assertEquals(database.findUser("ZaynRekhi123").getPosts().get(0).getLikes(), 1);
    }

    @Test
    public void testUnLikePost() {
        User user = database.findUser("ZaynRekhi123");
        Post post = user.getPosts().get(0);

        DataTransfer dt = new DataTransfer("POST LIKEPOST", post);
        DataTransfer outLike = server.processCommands(dt);

        DataTransfer dtd = new DataTransfer("POST UNLIKEPOST", outLike.getValue());
        DataTransfer outDislike = server.processCommands(dtd);

        assertEquals(outLike.getMessage(), "SUCCESS"); // Request went through
        assertEquals(outDislike.getMessage(), "SUCCESS"); // Request went through
        assertEquals(database.findUser("ZaynRekhi123").getPosts().get(0).getLikes(), 0);
    }

    @Test
    public void testDislikePost() {
        User user = database.findUser("ZaynRekhi123");
        Post post = user.getPosts().get(0);

        DataTransfer dt = new DataTransfer("POST DISLIKEPOST", post);
        DataTransfer out = server.processCommands(dt);

        assertEquals(out.getMessage(), "SUCCESS"); // Request went through
        assertEquals(database.findUser("ZaynRekhi123").getPosts().get(0).getDislikes(), 1);
    }

    @Test
    public void testUnDisLikePost() {
        User user = database.findUser("ZaynRekhi123");
        Post post = user.getPosts().get(0);

        DataTransfer dt = new DataTransfer("POST DISLIKEPOST", post);
        DataTransfer outLike = server.processCommands(dt);

        DataTransfer dtd = new DataTransfer("POST UNDISLIKEPOST", outLike.getValue());
        DataTransfer outDislike = server.processCommands(dtd);

        assertEquals(outLike.getMessage(), "SUCCESS"); // Request went through
        assertEquals(outDislike.getMessage(), "SUCCESS"); // Request went through
        assertEquals(database.findUser("ZaynRekhi123").getPosts().get(0).getDislikes(), 0);
    }

    @Test
    public void testFindFriendsPosts() {
        User user = database.findUser("ZaynRekhi123");
        User friend1 = database.findUser("brandon");
        User friend2 = database.findUser("SriMadur");

        ArrayList<User> users = new ArrayList<>();
        users.add(user);
        users.add(friend1);
        users.add(friend2);

        DataTransfer dt = new DataTransfer("USER ADDFRIEND", users);
        DataTransfer out = server.processCommands(dt);

        DataTransfer ndt = new DataTransfer("USER GETFRIENDSFEED", user);
        DataTransfer feedOut = server.processCommands(ndt);


        User f = database.findUser("ZaynRekhi123");
        ArrayList<Post> feed = (ArrayList<Post>) feedOut.getValue();

        assertEquals(out.getMessage(), "SUCCESS");
        assertEquals(feed, f.getFriendsFeed());
    }

    @Test
    public void testCreateComment() {
        User user1 = database.findUser("ZaynRekhi123");
        User user2 = database.findUser("SriMadur");
        User user3 = database.findUser("brandon");

        assertEquals(user1.getPosts().get(0).getComments().size(), 1);
        assertEquals(user2.getPosts().get(0).getComments().size(), 1); // Finds User in Database
        assertEquals(user3.getPosts().get(0).getComments().size(), 1); // Finds User in Database
    }

    @Test
    public void testDeleteComment() {
        User user = database.findUser("ZaynRekhi123");
        Post post = user.getPosts().get(0);
        Comment comment = post.getComments().get(0);

        ArrayList<Object> values = new ArrayList<Object>();
        values.add(comment);
        values.add(user);

        DataTransfer dt = new DataTransfer("COMMENT DELETECOMMENT", values);
        DataTransfer out = server.processCommands(dt);

        assertEquals(out.getMessage(), "SUCCESS"); // Request went through
        assertEquals(database.findUser("ZaynRekhi123").getPosts().get(0).getComments().size(), 0);
    }

    @Test
    public void testEditComment() {
        User user = database.findUser("ZaynRekhi123");
        Post post = user.getPosts().get(0);
        Comment comment = post.getComments().get(0);

        ArrayList<Object> values = new ArrayList<Object>();
        values.add(comment);
        values.add("New Msg");
        values.add("10-08-2024");

        DataTransfer dt = new DataTransfer("COMMENT EDITCOMMENT", values);
        DataTransfer out = server.processCommands(dt);

        assertEquals(out.getMessage(), "SUCCESS"); // Request went through
        assertTrue(database.findUser("ZaynRekhi123").getPosts().get(0).getComments().get(0).isEdited());
    }

    @Test
    public void testLikeComment() {
        User user = database.findUser("ZaynRekhi123");
        Post post = user.getPosts().get(0);
        Comment comment = post.getComments().get(0);

        DataTransfer dt = new DataTransfer("COMMENT LIKECOMMENT", comment);
        DataTransfer out = server.processCommands(dt);

        assertEquals(out.getMessage(), "SUCCESS"); // Request went through
        assertEquals(database.findUser("ZaynRekhi123").getPosts().get(0).getComments().get(0).getLikes(), 1);
    }

    @Test
    public void testDislikeComment() {
        User user = database.findUser("ZaynRekhi123");
        Post post = user.getPosts().get(0);
        Comment comment = post.getComments().get(0);

        DataTransfer dt = new DataTransfer("COMMENT DISLIKECOMMENT", comment);
        DataTransfer out = server.processCommands(dt);

        assertEquals(out.getMessage(), "SUCCESS"); // Request went through
        assertEquals(database.findUser("ZaynRekhi123").getPosts().get(0).getComments().get(0).getDislikes(), 1);
    }


//    @Test
//    public void testProcessCommands() {
//        User user1 = new User("bbraff", "gobears", "hi i'm brayden");
//        User user2 = new User("zaynr", "imzayn", "hi i'm zayn");
//        Post post1 = new Post(user1, "my cs180 project", "this is all about my project for cs180", "11-14-2024");
//        user1.post(post1);
//
//        Database d = new Database();
//        d.addUser(user1);
//        d.addUser(user2);
//        /*
//        Comment comment2 = new Comment(user2, post1, "hey i'm there too", "11-14-2024");
//        post1.addComment(comment2);
//        */
//
//
//        ArrayList<Object> a = new ArrayList<Object>();
//        a.add(user2);
//        a.add(post1);
//        a.add("hey i'm there too");
//        a.add("11-14-2024");
//
//        DataTransfer data = new DataTransfer("COMMENT MAKECOMMENT", a);
//        Server s = new Server(null);
//        DataTransfer out = s.processCommands(data);
//
//        assertEquals(out, 123);
//    }


}
