import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class Server extends Thread {
    private Socket clientSocket;
    private Database database = new Database();

    public Server(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public DataTransfer processUser(String action, String command, DataTransfer data) {
        if (action.equals("GET") && command.equals("LOGIN")) {
            ArrayList<String> values = ((ArrayList<String>) data.getValue());

            String username = values.get(0);
            String password = values.get(1);

            if (database.checkLogin(username, password)) {
                User user = database.findUser(username);
                return new DataTransfer(200, "SUCCESS", user);
            } else {
                return new DataTransfer(401, "FAILURE", "Invalid username or password");
            }
        }

        if (action.equals("CREATE") && command.equals("NEW_USER")) {
            ArrayList<String> values = ((ArrayList<String>) data.getValue());

            String username = values.get(0);
            String password = values.get(1);
            String bio = values.get(2);

            boolean valid = database.createUser(username, password, bio);

            if (valid) {
                return new DataTransfer(200, "SUCCESS", database.findUser(username));
            } else {
                String msg = "User Already Exists or Credentials are Invalid";
                return new DataTransfer(401, "FAILURE", msg);
            }
        }

        if (action.equals("DELETE") && command.equals("REMOVE_USER")) {
            User value = (User) data.getValue();

            boolean success = database.removeUser(value);

            if (success) {
                return new DataTransfer(200, "SUCCESS", value);
            } else {
                return new DataTransfer(401, "FAILURE", "User not found");
            }
        }

        return new DataTransfer(401, "FAILURE", "Command NOT Found!");
    }


    public DataTransfer processCommands(DataTransfer inp) {
        String[] commands = inp.getMessage().split(" ");

        String scope = commands[0];
        String action = commands[1];
        String command = commands[2];

        DataTransfer out = null;
        switch (scope) {
            case "USER" -> {
                out = processUser(action, command, inp);
            }

            case "POST" -> {

            }

            case "COMMENT" -> {

            }

            default -> {
                out = new DataTransfer(401, "FAILURE", "Invalid command");
            }
        }

        return out;
    }


    @Override
    public void run() {
        try {
            ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream());
            ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream());

            while (true) {
                DataTransfer data = (DataTransfer) ois.readObject();

                if (data.getMessage().equals("TERMINATED"))
                    break;

                DataTransfer out = null;

                try {
                    out = processCommands(data);
                } catch (Exception e) {
                    out = new DataTransfer(401, "COMMAND FAILURE", e.getMessage());
                }

                oos.writeObject(out);
                oos.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
