public interface ClientInterface {
    public DataTransfer awaitResponse();
    public boolean writeObject(DataTransfer data);
    public DataTransfer request(DataTransfer data);
    public void close();
    public void accept();
}
