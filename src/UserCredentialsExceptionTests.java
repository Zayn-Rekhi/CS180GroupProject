import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * Simply testing if the custom Exception is created properly by comparing the message it uses to display
 * when it is thrown with the original message. Testing whether the exception message matches what is expected.
 * <p>
 * Checks if the create user method in database is passed in usernames and passwords that are not long enough,
 * (<6 characters), than throw an exception but it shouldn't stop the program. Does the same for the User
 * constructor.
 *
 * @author zaynrekhi
 * @author melody
 * @author srimadur
 * @author braydenbrafford
 * @author nothanlee
 * @version 1.0.0
 */
public class UserCredentialsExceptionTests implements UserCredentialsExceptionTestsInterface {

    @Test
    public void testExceptionMessageType1() { //Testing whether the exception message matches what is expected.
        String errorMessage = "Username and password cannot be empty!";

        UserCredentialsException dateFormatException = new UserCredentialsException(errorMessage);
        assertEquals(errorMessage, dateFormatException.getMessage());
    }

    @Test
    public void testExceptionMessageType2() { //Testing whether the exception message matches what is expected.
        String errorMessage = "Username and password must be at least 6 characters!";

        UserCredentialsException dateFormatException = new UserCredentialsException(errorMessage);
        assertEquals(errorMessage, dateFormatException.getMessage());
    }

    @Test
    public void testExceptionMessageType3() { //Testing whether the exception message matches what is expected.
        String errorMessage = "Username or password contains spaces!";

        UserCredentialsException dateFormatException = new UserCredentialsException(errorMessage);
        assertEquals(errorMessage, dateFormatException.getMessage());
    }

    @Test
    public void testExceptionMessageType4() { //Testing whether the exception message matches what is expected.
        String errorMessage = "User Credentials are Invalid!";

        UserCredentialsException dateFormatException = new UserCredentialsException(errorMessage);
        assertEquals(errorMessage, dateFormatException.getMessage());
    }

    @Test
    public void testDoesNotThrowException() {
        assertDoesNotThrow(() -> {
            new User("zre", "zayn12345", "Goes to Purdue");
        });

        assertDoesNotThrow(() -> {
            new User("zrekhi234", "za", "Goes to Purdue");
        });

        assertDoesNotThrow(() -> {
            Database db = new Database();
            db.createUser("zr", "zr12345", "I am from India");
            db.createUser("zaynrekhi", "zr1", "I am from India");

        });
    }
}