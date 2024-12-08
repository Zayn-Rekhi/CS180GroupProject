/**
 * UserAlreadyExists Class
 * The UserAlreadyExists exception is used when the user is already in the ArrayList of users in the database,
 * so we can't add another User with the same username to it again.
 *
 * @author zaynrekhi
 * @author melody
 * @author srimadur
 * @author braydenbrafford
 * @author nothanlee
 * @version 1.0.0
 */
public class UserAlreadyExistsException extends Exception implements UserAlreadyExistsExceptionInterface {
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
