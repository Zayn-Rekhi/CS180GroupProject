/**
 * Client Tests Interface
 * contains all the methods to be implemented in the Client Tests class
 *
 * @author zaynrekhi
 * @author melody
 * @author srimadur
 * @author braydenbrafford
 * @author nothanlee
 * @version 1.0.0
 *
 * @version 1.0.0
 */
public interface ClientTestsInterface {
    void testConstructor();
    void testWriteObject_Success();
    void testWriteObject_Failure();
    void testAwaitResponse_Success();
    void testAwaitResponse_Failure();
    void testRequest_Success();
    void testRequest_Failure();
    void testClose();
}
