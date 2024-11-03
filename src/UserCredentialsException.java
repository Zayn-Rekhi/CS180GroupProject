/**
 * UserCredentialsException Class
 * The UserCredentialsException exception is used when the user tries to input invalid credentials,
 * Furthermore, they have to enter a password and a username that have to be greater than 6 characters
 * without any spaces.
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
public class UserCredentialsException extends Exception implements UserCredentialsExceptionInterface {
    public UserCredentialsException(String message) {
        super(message);
    }
}
