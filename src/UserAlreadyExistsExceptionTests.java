import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/**
 * Simply testing if the custom Exception is created properly by comparing the message it uses to display
 * when it is thrown with the original message. Testing whether the exception message matches what is expected.
 *
 * @author zaynrekhi
 *
 * @version 1.0.0
 */
public class UserAlreadyExistsExceptionTests implements UserAlreadyExistsExceptionTestsInterface{

    @Test
    public void testExceptionMessage() { //Testing whether the exception message matches what is expected.
        String errorMessage = "User Already Exists in Database!";

        UserAlreadyExistsException userException = new UserAlreadyExistsException(errorMessage);
        assertEquals(errorMessage, userException.getMessage());
    }
}