import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/**
 * Simply testing if the custom Exception is created properly by comparing the message it uses to display
 * when it is thrown with the original message. Testing whether the exception message matches what is expected.
 *
 * This also tests if the dates passed as an argument to the Comment constructor and Post constructor
 * are incorrectly formatted, it should not throw an exception.
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
        String errorMessage = "Date is incorrectly formatted! Make sure it is 00-00-0000 (Month-Day-Year)";

        DateFormatException dateFormatException = new DateFormatException(errorMessage);
        assertEquals(errorMessage, dateFormatException.getMessage());

    }

    @Test
    public void testDoesNotThrowException() {
        assertDoesNotThrow(() -> {
            String format1 = "01/20/24";
            String format2 = "01-20/25";

            User u = new User("zaynrekhi", "assimo11!", "I love books!");
            Post p = new Post(u, "The Best Date", "https://example.com/pfp.jpg", format1);
            Comment c = new Comment(u, p, "Mee too!!!!", format2);
        });
    }
}