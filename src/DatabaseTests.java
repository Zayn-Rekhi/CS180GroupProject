import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;


public class DatabaseTests {
    private Database db;

    @BeforeEach
    public void setUp() {
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
        db.createUser("ZaynRekhi", "assimo11234!", "The Best");
        assertTrue(db.checkLogin("ZaynRekhi", "assimo11234!"));
    }

    @Test
    public void testCreateUser() {
        db.clear();
        assertFalse(db.createUser("ZR", "Assimo11234!", "The Best"));
        assertFalse(db.createUser("ZaynRekhi123", "as!", "The Best"));
    }

    @Test
    public void testModifyUser() {
        db.clear();

        User oldUser = new User("ZaynRekhi", "assimo11234!", "I love books!");
        User newUser = new User("ZaynRekhi", "assimo11234!", "I love books and outdoors!");


        db.addUser(oldUser);
        db.modifyUser(oldUser, newUser);

        ArrayList<User> comp = new ArrayList<>();
        comp.add(newUser);

        assertEquals(comp, db.getUsers());
    }

    @Test
    public void testRemoveUser() {
        db.clear();

        User user = new User("ZaynRekhi", "assimo11234!", "I love books!");


        db.addUser(user);
        db.removeUser(user);

        ArrayList<User> comp = new ArrayList<>();

        assertEquals(comp, db.getUsers());
    }

    @Test
    public void testDataPersistence() {
        User user1 = new User("ZaynRekhi", "assimo11234!", "I love books!");
        User user2 = new User("zrekhi123", "theotherone!", "I love books and outdoors!");
        User user3 = new User("zayntherekhi", "people123!", "I love books and outdoors!");


        db.addUser(user1);
        db.addUser(user2);
        db.addUser(user3);

        Database newDB = new Database("src/data/data.txt");
        newDB.loadUsers();

        assertEquals(newDB.getUsers(), db.getUsers());
    }

}