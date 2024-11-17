/**
 * Server Interface
 * contains all the methods to be implemented in the Server class
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
public interface ServerInterface {
    DataTransfer processUser(String command, DataTransfer data);
    DataTransfer processPost(String command, DataTransfer data);
    DataTransfer processComment(String command, DataTransfer data);
    DataTransfer processCommands(DataTransfer data);
}
