import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class Client {
    private Socket socket;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;


    public Client(String username, int port) throws IOException {
        socket = new Socket(username, port);
        ois = new ObjectInputStream(socket.getInputStream());
        oos = new ObjectOutputStream(socket.getOutputStream());
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

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Client client = new Client("localhost", 4242);

        DataTransfer out = client.awaitResponse();

        System.out.println(out.getMessage());
        ArrayList<String> array = new ArrayList<>();
        array.add("Zayn123sdafjas123");
        array.add("Assimo11234!");
        array.add("asfasf1231!");

        DataTransfer o = client.request(new DataTransfer("USER CREATENEWUSER", array));
        System.out.println(o.getMessage());

        client.close();

    }
}
