import java.io.*;
import java.util.ArrayList;

/**
 * Database Class
 * The Database class manages a many User objects with by using a static ArrayList and
 * a synchronized lock for concurrency. It can  add, remove, modify, and authenticate users, as
 * well as load and store data from a file specified by fileName. The class also handles user creation
 * with usernames and maintains constantly updates the data file whenever user data is changed to ensure
 * nothing is lost.
 *
 * @author zaynrekhi
 * @author melody
 * @author srimadur
 * @author braydenbrafford
 * @author nothanlee
 * @version 1.0.0
 */

public class Database implements DatabaseInterface {
    private static ArrayList<User> users;
    private static final String FILENAME = "src/data/data.txt";
    private static final Object LOCK = new Object();


    public Database() {
        synchronized (LOCK) {
            if (users == null) {
                users = new ArrayList<User>();
                this.loadUsers();
            }
        }
    }

    public boolean updateDataFile() {
        try (FileOutputStream fin = new FileOutputStream(this.FILENAME);
             ObjectOutputStream oit = new ObjectOutputStream(fin)) {

            synchronized (LOCK) {
                oit.writeObject(users);
                oit.flush();
            }

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public boolean loadUsers() {
        try (FileInputStream fin = new FileInputStream(this.FILENAME);
             ObjectInputStream oit = new ObjectInputStream(fin)) {

            synchronized (LOCK) {
                users = (ArrayList<User>) oit.readObject();
            }

            return true;

        } catch (IOException | ClassNotFoundException e) {
            return false;
        }
    }

    public boolean checkLogin(String username, String password) {
        synchronized (LOCK) {
            System.out.println(users);
            for (User user : users) {
                if (user.getUserName().equals(username) && user.getPassword().equals(password)) {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean addUser(User user) {
        try {
            synchronized (LOCK) {
                users.add(user);
            }

            this.updateDataFile();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean modifyUser(User newUser, User prevUser) {
        try {
            synchronized (LOCK) {
                for (int i = 0; i < users.size(); i++) {
                    User currentUser = users.get(i);

                    if (currentUser.getUserName().equals(prevUser.getUserName())) {
                        users.set(i, newUser);
                    } else {
                        for (User f : currentUser.getFriends()) {
                            if (f.getUserName().equals(prevUser.getUserName())) {
                                currentUser.removeFriend(f);
                                currentUser.addFriend(newUser);
                            }
                        }

                        for (User f : currentUser.getBlocked()) {
                            if (f.getUserName().equals(prevUser.getUserName())) {
                                currentUser.unBlock(f);
                                currentUser.block(newUser);
                            }
                        }

                        for (Post p : currentUser.getPosts()) {
                            if (p.getUser().getUserName().equals(prevUser.getUserName())) {
                                p.setUser(newUser);
                            }

                            ArrayList<User> likedUsers = p.getLikedUsers();
                            for (int x = 0; x < likedUsers.size(); x++) {
                                if (likedUsers.get(x).getUserName().equals(prevUser.getUserName())) {
                                    likedUsers.set(x, newUser);
                                }
                            }
                            p.setLikedUsers(likedUsers);

                            for (Comment c : p.getComments()) {
                                if (c.getCommenter().getUserName().equals(prevUser.getUserName())) {
                                    c.setCommenter(newUser);
                                }

                                ArrayList<User> likedUsersComments = c.getLikedUsers();
                                for (int n = 0; n < likedUsersComments.size(); n++) {
                                    if (likedUsers.get(n).getUserName().equals(prevUser.getUserName())) {
                                        likedUsers.set(n, newUser);
                                    }
                                }
                                c.setLikedUsers(likedUsersComments);

                            }
                        }

                        users.set(i, currentUser);
                    }
                }
            }

            this.updateDataFile();
            return true;
        } catch (IndexOutOfBoundsException e) {
            return false;
        }
    }

    public boolean removeUser(User user) {
        try {
            synchronized (LOCK) {
                users.remove(user);
            }

            this.updateDataFile();
            return true;
        } catch (IndexOutOfBoundsException e) {
            return false;
        }
    }

    public boolean createUser(String username, String password, String bio) {
        try {
            User s = new User(username, password, bio);

            if (findUser(username) != null) {
                throw new UserAlreadyExistsException("User Already Exists in Database!");
            }

            if (s.getUserName() == null) {
                throw new UserCredentialsException("User Credentials are Invalid!");
            }

            this.addUser(s);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public User findUser(String username) {
        synchronized (LOCK) {
            for (User user : users) {
                if (user.getUserName().equals(username)) {
                    return user;
                }
            }

        }
        return null;
    }

    public ArrayList<User> getUsers() {
        synchronized (LOCK) {
            return users;
        }
    }

    public void clear() {
        synchronized (LOCK) {
            users.clear();
            this.updateDataFile();
        }
    }
}
