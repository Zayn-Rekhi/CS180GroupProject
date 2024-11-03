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
public class UserCredentialsExceptionTests implements UserCredentialsExceptionTestsInterface {

    @Test
    public void testExceptionMessage1() { //Testing whether the exception message matches what is expected.
        String errorMessage = "Username and password cannot be empty!";

        UserCredentialsException dateFormatException = new UserCredentialsException(errorMessage);
        assertEquals(errorMessage, dateFormatException.getMessage());
    }

    @Test
    public void testExceptionMessage2() { //Testing whether the exception message matches what is expected.
        String errorMessage = "Username and password must be at least 6 characters!";

        UserCredentialsException dateFormatException = new UserCredentialsException(errorMessage);
        assertEquals(errorMessage, dateFormatException.getMessage());
    }

    @Test
    public void testExceptionMessage3() { //Testing whether the exception message matches what is expected.
        String errorMessage = "Username or password contains spaces!";

        UserCredentialsException dateFormatException = new UserCredentialsException(errorMessage);
        assertEquals(errorMessage, dateFormatException.getMessage());
    }
}