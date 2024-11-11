import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
/**
 * Database Tests Interface
 * contains all the methods to be implemented in the Database Tests class
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
public interface DatabaseTestsInterface {
    void setUp();
    void testClearUsers();
    void testLoadUsers();
    void testCheckLogin();
    void testCreateUser();
    void testFindUser();
    void testModifyUser();
    void testRemoveUser();
    void testDataPersistence();
}
