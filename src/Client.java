import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Client Class
 * This class represents the client-side of the platform and 
 * allows the users to interact with the server through commands.  
 * It sends requests encapsulated in DataTransfer objects to 
 * the server and processes responses to update the database.  
 * It handles all the communication with the Server class through 
 * sockets and ensures the functionality of user operations.
 * It implements the ClientInterface which contains all the methods that 
 * are used in this class.
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

public class Client implements ClientInterface {
    private Socket socket;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;

    public Client(String host, int port) {
        try {
            socket = new Socket(host, port);
            ois = new ObjectInputStream(socket.getInputStream());
            oos = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Constructor for testing
    public Client(Socket socket, ObjectInputStream ois, ObjectOutputStream oos) {
        this.socket = socket;
        this.ois = ois;
        this.oos = oos;
    }

    public DataTransfer awaitResponse() {
        try {
            DataTransfer out = (DataTransfer) ois.readObject();
            return out;
        } catch (ClassNotFoundException | IOException e) {
            return new DataTransfer("Bad Response", null);
        }
    }

    public boolean writeObject(DataTransfer data) {
        try {
            oos.writeObject(data);
            oos.flush();

            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public DataTransfer request(DataTransfer data) {
        boolean val = this.writeObject(data);
        if (val)
            return this.awaitResponse();
        else
            return new DataTransfer("Bad Request", null);
    }

    public void close() {
        if (oos != null) { // Add null check to avoid NullPointerException
            writeObject(new DataTransfer("TERMINATED", null));
        }
    }

    public void accept() {
        DataTransfer out = this.awaitResponse();
        if (!out.getMessage().equals("CONNECTED"))
            throw new RuntimeException("Could not Accept Server Socket Request");
    }

    public static void main(String[] args) {
        Client client2 = new Client("localhost", 4242);
        client2.accept();
        client2.close();

    }
}
