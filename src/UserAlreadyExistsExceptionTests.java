import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * Simply testing if the custom Exception is created properly by comparing the message it uses to display
 * when it is thrown with the original message. Testing whether the exception message matches what is expected.
 * <p>
 * This also tests that creating the same user multiple times in the createUser function of database
 * doesn't raise any exceptions.
 *
 * @author zaynrekhi
 * @author melody
 * @author srimadur
 * @author braydenbrafford
 * @author nothanlee
 * @version 1.0.0
 */
public class UserAlreadyExistsExceptionTests implements UserAlreadyExistsExceptionTestsInterface {

    @Test
    public void testExceptionMessage() { //Testing whether the exception message matches what is expected.
        String errorMessage = "User Already Exists in Database!";

        UserAlreadyExistsException userException = new UserAlreadyExistsException(errorMessage);
        assertEquals(errorMessage, userException.getMessage());
    }

    @Test
    public void testDoesNotThrowException() {
        Database db = new Database();
        db.createUser("zaynrekhi", "zr12345", "I am from India");

        assertDoesNotThrow(() -> {
            db.createUser("zaynrekhi", "zr12345", "I am from India");
        });
    }
}