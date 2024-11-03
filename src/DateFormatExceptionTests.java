import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/**
 * Simply testing if the custom Exception is created properly by comparing the message it uses to display
 * when it is thrown with the original message. Testing whether the exception message matches what is expected.
 *
 * @author zaynrekhi
 * @author melody
 * @author srimadur
 * @author braydenbrafford
 * @author nothanlee
 * @version 1.0.0
 * @version 1.0.0
 */
public class DateFormatExceptionTests implements DateFormatExceptionTestsInterface {

    @Test
    public void testExceptionMessage() {
        String errorMessage = "Date is incorrectly formatted! Make sure it is 00/00/0000 (Month/Day/Year)";

        DateFormatException dateFormatException = new DateFormatException(errorMessage);
        assertEquals(errorMessage, dateFormatException.getMessage());
    }
}