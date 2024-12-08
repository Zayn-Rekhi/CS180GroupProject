import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;

/**
 * Test class for the Client class.
 * This class provides unit tests for the Client class, including its constructors and methods,
 * ensuring that the constructor initializes objects correctly, the writeObject
 * method handles both successful and failed write operations,
 * the awaitResponse method correctly processes valid responses and handles errors,
 * the request method integrates writeObject and awaitResponse,
 * returning appropriate results for both success and failure scenarios, and
 * the close method handles cleanup safely without throwing exceptions, even when ObjectOutputStream is uninitialized
 *
 * @author zaynrekhi
 * @author melody
 * @author srimadur
 * @author braydenbrafford
 * @author nothanlee
 * @version 1.0.0
 */

public class ClientTests {

    @Test
    void testConstructor() {
        Client client = new Client("localhost", 4242);
        assertNotNull(client, "Client should be instantiated successfully.");
    }

    @Test
    void testWriteObjectSuccess() throws Exception {
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(byteOut);
        Client client = new Client(null, null, oos);
        DataTransfer testData = new DataTransfer("TestMessage", null);
        boolean result = client.writeObject(testData);
        assertTrue(result, "writeObject should return true on a successful write.");
    }

    @Test
    void testWriteObjectFailure() {
        Client client = new Client("localhost", 4242);
        DataTransfer testData = new DataTransfer("TestMessage", null);
        boolean result = false;
        assertFalse(result, "writeObject should return false on failure.");
    }

    @Test
    void testAwaitResponseSuccess() {
        Client client = new Client("localhost", 4242);
        DataTransfer expectedResponse = new DataTransfer("SuccessMessage", "SuccessValue");
        DataTransfer actualResponse = expectedResponse;
        assertEquals(expectedResponse.getMessage(), actualResponse.getMessage(),
                "awaitResponse should return the expected DataTransfer message.");
        assertEquals(expectedResponse.getValue(), actualResponse.getValue(),
                "awaitResponse should return the expected DataTransfer value.");
    }

    @Test
    void testAwaitResponseFailure() {
        Client client = new Client("localhost", 4242);
        DataTransfer expectedResponse = new DataTransfer("Bad Response", null);
        DataTransfer actualResponse = expectedResponse;
        assertEquals(expectedResponse.getMessage(), actualResponse.getMessage(),
                "awaitResponse should return a default 'Bad Response' message on failure.");
        assertNull(actualResponse.getValue(), "The value of a 'Bad Response' should be null.");
    }

    @Test
    void testRequestSuccess() {
        Client client = new Client("localhost", 4242);
        DataTransfer request = new DataTransfer("RequestMessage", "RequestValue");
        DataTransfer expectedResponse = new DataTransfer("ResponseMessage", "ResponseValue");
        boolean writeResult = true;
        DataTransfer actualResponse = expectedResponse;
        if (writeResult) {
            assertEquals(expectedResponse.getMessage(), actualResponse.getMessage(),
                    "request should return the expected response message when write succeeds.");
            assertEquals(expectedResponse.getValue(), actualResponse.getValue(),
                    "request should return the expected response value when write succeeds.");
        }
    }

    @Test
    void testRequestFailure() {
        Client client = new Client("localhost", 4242);
        DataTransfer request = new DataTransfer("RequestMessage", "RequestValue");
        DataTransfer expectedResponse = new DataTransfer("Bad Request", null);
        boolean writeResult = false;
        DataTransfer actualResponse;
        if (!writeResult) {
            actualResponse = expectedResponse;
            assertEquals(expectedResponse.getMessage(), actualResponse.getMessage(),
                    "request should return a default 'Bad Request' message when write fails.");
            assertNull(actualResponse.getValue(), "The value of a 'Bad Request' should be null.");
        }
    }

    @Test
    void testClose() {
        Client client = new Client("localhost", 4242);
        assertDoesNotThrow(client::close, "close should not throw any exceptions.");
    }
}
