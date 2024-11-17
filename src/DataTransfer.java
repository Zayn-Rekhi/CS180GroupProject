import java.io.Serializable;

/**
 * Data Transfer Class
 * This class handles a message and a value to manage data transfer between different parts of the social media app. 
 * It supports serialization for storing or sending data and contains getter methods to access the message and value.
 *
 * @author zaynrekhi
 * @author melody
 * @author srimadur
 * @author braydenbrafford
 * @author nothanlee
 * @version 1.0.0
 *
 */

public class DataTransfer implements Serializable, DataTransferInterface {
    private String message;
    private Object value;

    public DataTransfer(String message, Object value) {
        this.message = message;
        this.value = value;
    }

    public String getMessage() {
        return message;
    }

    public Object getValue() {
        return value;
    }
}
