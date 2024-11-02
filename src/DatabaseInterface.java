public interface DatabaseInterface {
    boolean addUser(User user);
    boolean loadUsers();
    boolean checkLogin(String username, String password);
    boolean createUser(String username, String password);
    boolean modifyUser(User prevUser, User newUser);
    boolean removeUser(User user);
    User findUser(String username);
}
