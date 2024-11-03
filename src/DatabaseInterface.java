/**
 * Database Interface
 * contains all the methods to be implemented in the Database class
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
public interface DatabaseInterface {
    boolean addUser(User user);
    boolean loadUsers();
    boolean checkLogin(String username, String password);
    boolean createUser(String username, String password, String bio);
    boolean modifyUser(User prevUser, User newUser);
    boolean removeUser(User user);
    User findUser(String username);
}
