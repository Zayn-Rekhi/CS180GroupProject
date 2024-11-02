import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;


public class DatabaseTests {
    private Database db;

    @BeforeEach
    public void setUp() {
        User.setUserIDCounter(0);
        db = new Database("src/data/data.txt");
    }

    @Test
    public void testLoadUsers() {
        db.clear();
        db.loadUsers();
        assertEquals(new ArrayList<User>(), db.getUsers());
    }

    @Test
    public void testCheckLogin() {
        db.clear();
        db.createUser("ZaynRekhi", "assimo11234!");
        assertTrue(db.checkLogin("ZaynRekhi", "assimo11234!"));
    }

    @Test
    public void testCreateUser() {
        db.clear();
        assertFalse(db.createUser("ZR", "Assimo11234!"));
        assertFalse(db.createUser("ZaynRekhi123", "as!"));
    }

    @Test
    public void testModifyUser() {
        db.clear();
        User oldUser = null;
        User newUser = null;
        try {
            oldUser = new User("ZaynRekhi", "assimo11234!", "I love books!");
            newUser = new User("ZaynRekhi", "assimo11234!", "I love books and outdoors!");
        } catch (UserCredentialsException e) {
            e.printStackTrace();
        }

        db.addUser(oldUser);
        db.modifyUser(oldUser, newUser);

        ArrayList<User> comp = new ArrayList<>();
        comp.add(newUser);

        assertEquals(comp, db.getUsers());
    }

    @Test
    public void testRemoveUser() {
        db.clear();
        User user = null;

        try {
            user = new User("ZaynRekhi", "assimo11234!", "I love books!");
        } catch (UserCredentialsException e) {
            e.printStackTrace();
        }

        db.addUser(user);
        db.removeUser(user);

        ArrayList<User> comp = new ArrayList<>();

        assertEquals(comp, db.getUsers());
    }

    @Test
    public void testDataPersistence() {
        User user1 = null;
        User user2 = null;
        User user3 = null;
        try {
            user1 = new User("ZaynRekhi", "assimo11234!", "I love books!");
            user2 = new User("zrekhi123", "theotherone!", "I love books and outdoors!");
            user3 = new User("zayntherekhi", "people123!", "I love books and outdoors!");
        } catch (UserCredentialsException e) {
            e.printStackTrace();
        }

        db.addUser(user1);
        db.addUser(user2);
        db.addUser(user3);

        Database newDB = new Database("src/data/data.txt");
        newDB.loadUsers();

        assertEquals(newDB.getUsers(), db.getUsers());
    }

}