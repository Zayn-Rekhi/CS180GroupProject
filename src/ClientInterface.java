/*
java doc
*/
public interface ClientInterface {
    DataTransfer awaitResponse();
    boolean writeObject(DataTransfer data);
    DataTransfer request(DataTransfer data);
    void close();
    void accept();
}
