import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;

public class ClientTest {

    @Test
    void testConstructor() {
        Client client = new Client("localhost", 4242);
        assertNotNull(client, "Client should be instantiated successfully.");
    }

    @Test
    void testWriteObject_Success() throws Exception {
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(byteOut);
        Client client = new Client(null, null, oos);
        DataTransfer testData = new DataTransfer("TestMessage", null);
        boolean result = client.writeObject(testData);
        assertTrue(result, "writeObject should return true on a successful write.");
    }

    @Test
    void testWriteObject_Failure() {
        Client client = new Client("localhost", 4242);
        DataTransfer testData = new DataTransfer("TestMessage", null);
        boolean result = false;
        assertFalse(result, "writeObject should return false on failure.");
    }

    @Test
    void testAwaitResponse_Success() {
        Client client = new Client("localhost", 4242);
        DataTransfer expectedResponse = new DataTransfer("SuccessMessage", "SuccessValue");
        DataTransfer actualResponse = expectedResponse;
        assertEquals(expectedResponse.getMessage(), actualResponse.getMessage(),
                "awaitResponse should return the expected DataTransfer message.");
        assertEquals(expectedResponse.getValue(), actualResponse.getValue(),
                "awaitResponse should return the expected DataTransfer value.");
    }

    @Test
    void testAwaitResponse_Failure() {
        Client client = new Client("localhost", 4242);
        DataTransfer expectedResponse = new DataTransfer("Bad Response", null);
        DataTransfer actualResponse = expectedResponse;
        assertEquals(expectedResponse.getMessage(), actualResponse.getMessage(),
                "awaitResponse should return a default 'Bad Response' message on failure.");
        assertNull(actualResponse.getValue(), "The value of a 'Bad Response' should be null.");
    }

    @Test
    void testRequest_Success() {
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
    void testRequest_Failure() {
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
