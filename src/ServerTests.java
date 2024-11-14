import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;

/**
 * Server Test Class
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
public class ServerTests {
    @Test
    public void testProcessCommands() {
        var a =  new ArrayList<String>();
        a.add("ZaynRekhi123");
        a.add("12323432");

        DataTransfer data = new DataTransfer("GETLOGIN", a);
        Server s = new Server(null);
        DataTransfer out = s.processCommands(data);

        assertEquals(out, 123);
    }


}