/**
 * Client Interface
 * contains all the methods to be implemented in the Client class
 *
 * @author zaynrekhi
 * @author melody
 * @author srimadur
 * @author braydenbrafford
 * @author nothanlee
 * @version 1.0.0
 */

public interface ClientInterface {
    DataTransfer awaitResponse();

    boolean writeObject(DataTransfer data);

    DataTransfer request(DataTransfer data);

    void close();

    void accept();
}
