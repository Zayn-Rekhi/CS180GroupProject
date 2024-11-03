import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class UserAlreadyExistsTests implements UserAlreadyExistsTestsInterface{

    @Test
    public void testExceptionMessage() { //Testing whether the exception message matches what is expected.
        String errorMessage = "User Already Exists in Database!";

        UserAlreadyExists userException = new UserAlreadyExists(errorMessage);
        assertEquals(errorMessage, userException.getMessage());
    }
}