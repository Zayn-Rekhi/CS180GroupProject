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
 */
public interface ClientTestsInterface {
    void testConstructor();

    void testWriteObjectSuccess();

    void testWriteObjectFailure();

    void testAwaitResponseSuccess();

    void testAwaitResponseFailure();

    void testRequestSuccess();

    void testRequestFailure();

    void testClose();
}
