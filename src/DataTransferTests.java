import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;

/**
 * Comment Test Class
 * This class tests out all the methods in the Comment class to make sure that they are running smoothly with edge
 * cases. It ensures that the way in which comments, posts, and users are stringed together doesn't give rise to
 * any errors. More specifically, we test functionality such as initialization, adding and likes, getters and setters,
 * editing message, displaying comments, and delete comments by authorized users.
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

public class DataTransferTests implements DataTransferTestsInterface {

    @Test
    public void testDataTransferMessage() {
        String msg = "USER USERDATA";
        DataTransfer data = new DataTransfer(msg, null);

        assertEquals(msg, data.getMessage());
        assertNull(data.getMessage());
    }

    @Test
    public void testDataValue() {
        Object value = new ArrayList<String>();
        DataTransfer data = new DataTransfer(null, value);

        assertEquals(data.getValue(), value);
        assertNull(data.getMessage());
    }
}