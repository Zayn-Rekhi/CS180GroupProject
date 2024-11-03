import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class UserCredentialsExceptionTests {

    @Test
    void testExceptionMessage1() { //Testing whether the exception message matches what is expected.
        String errorMessage = "Username and password cannot be empty!";

        UserCredentialsException dateFormatException = new UserCredentialsException(errorMessage);
        assertEquals(errorMessage, dateFormatException.getMessage());
    }

    @Test
    void testExceptionMessage2() { //Testing whether the exception message matches what is expected.
        String errorMessage = "Username and password must be at least 6 characters!";

        UserCredentialsException dateFormatException = new UserCredentialsException(errorMessage);
        assertEquals(errorMessage, dateFormatException.getMessage());
    }

    @Test
    void testExceptionMessage3() { //Testing whether the exception message matches what is expected.
        String errorMessage = "Username or password contains spaces!";

        UserCredentialsException dateFormatException = new UserCredentialsException(errorMessage);
        assertEquals(errorMessage, dateFormatException.getMessage());
    }
}