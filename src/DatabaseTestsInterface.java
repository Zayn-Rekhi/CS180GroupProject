import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public interface DatabaseTestsInterface {
    void setUp();
    void testLoadUsers();
    void testCheckLogin();
    void testCreateUser();
    void testModifyUser();
    void testRemoveUser();
    void testDataPersistence();
}
