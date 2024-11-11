import java.io.*;
import java.net.Socket;

public class Server extends Thread {
    private Socket clientSocket;
    private Database database = new Database();

    public Server(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public DataTransfer process(DataTransfer inp) {
        return new DataTransfer(0, null, null);
    }


    @Override
    public void run() {
        try {
            ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream());
            ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream());

            while (true) {
                DataTransfer data = (DataTransfer) ois.readObject();
                DataTransfer out = process(data);
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
