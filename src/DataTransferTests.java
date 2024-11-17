import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;

/**
 * Data Transfer Test Class
 * Unit tests for Data Transfer Class, tests the all the methods in the class to make sure
 * the message and value fields are properly initialized, the getters for message and value
 * work correctly, handles null values for message and value correctly, and simultaneous use of
 * value and message fields.
 *
 * @author zaynrekhi
 * @author melody
 * @author srimadur
 * @author braydenbrafford
 * @author nathanlee
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
