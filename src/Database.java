package src;

import java.util.ArrayList;
import java.io.*;


public class Database  {

    // Fields
    private ArrayList<User> users;
    private String fileName;

    //Constructor

    public Database(String usersIn){
        this.fileName = fileName;
    }

    public void loadData() {


    }


    // Methods
    public void readUsers() {
        try {
            FileInputStream fin = new FileInputStream(this.fileName); 
            ObjectInputStream oit = new ObjectInputStream(fin); 

            this.users = oit.readObject(this.users);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void addUser(User user) {
        
    }

    public boolean removeUser(int userID) {
        return false;
    }

    public boolean login(String username, String password) {
        return false;
    }
}