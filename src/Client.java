import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client implements ClientInterface {
    private Socket socket;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;

    public Client(String username, int port) {
        try {
            socket = new Socket(username, port);
            ois = new ObjectInputStream(socket.getInputStream());
            oos = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        this.writeObject(new DataTransfer("TERMINATED", null));
    }

    public void accept() {
        DataTransfer out = this.awaitResponse();
        if (!out.getMessage().equals("CONNECTED"))
            throw new RuntimeException("Could not Accept Server Socket Request");
    }
}
