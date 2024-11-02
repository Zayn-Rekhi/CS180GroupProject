import java.io.*;
import java.util.ArrayList;

public class Database extends Thread implements DatabaseInterface {
    private static ArrayList<User> users;
    private String fileName;

    private static final Object lock = new Object();


    public Database(String fileName) {
        this.fileName = fileName;

        synchronized (lock) {
            users = new ArrayList<User>();
        }
    }

    public boolean updateDataFile() {
        try (FileOutputStream fin = new FileOutputStream(this.fileName);
             ObjectOutputStream oit = new ObjectOutputStream(fin)) {

            synchronized (lock) {
                oit.writeObject(users);
                oit.flush();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return true;
    }

    public boolean loadUsers() {
        try (FileInputStream fin = new FileInputStream(this.fileName);
             ObjectInputStream oit = new ObjectInputStream(fin)) {

            synchronized (lock) {
                users = (ArrayList<User>) oit.readObject();
            }

            return true;

        } catch (IOException | ClassNotFoundException e) {
            return false;
        }
    }

    public boolean checkLogin(String username, String password) {
        synchronized (lock) {
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
            synchronized (lock) {
                users.add(user);
            }

            this.updateDataFile();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean modifyUser(User prevUser, User newUser) {
        try {
            synchronized (lock) {
                for (int i = 0; i < users.size(); i++) {
                    if (users.get(i).getUserName().equals(prevUser.getUserName())) {
                        users.set(i, newUser);
                        break;
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
            synchronized (lock) {
                users.remove(user);
            }

            this.updateDataFile();
            return true;
        } catch (IndexOutOfBoundsException e) {
            return false;
        }
    }

    public boolean createUser(String username, String password) {
        try {
            User s = new User(username, password, null);

            if (findUser(username) != null) {
                throw new UserAlreadyExists("User Already Exists in Database!");
            }

            this.addUser(s);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public User findUser(String username) {
        synchronized (lock) {
            for (User user : users) {
                if (user.getUserName().equals(username)) {
                    return user;
                }
            }

        }
        return null;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void clear() {
        synchronized (lock) {
            users.clear();
            this.updateDataFile();
        }
    }
}
