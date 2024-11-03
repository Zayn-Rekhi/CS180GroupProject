import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class DateFormatExceptionTests {

    @Test
    void testExceptionMessage() { //Testing whether the exception message matches what is expected.
        String errorMessage = "Date is incorrectly formatted! Make sure it is 00/00/0000 (Month/Day/Year)";

        DateFormatException dateFormatException = new DateFormatException(errorMessage);
        assertEquals(errorMessage, dateFormatException.getMessage());
    }
}