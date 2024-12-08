import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

/**
 * UserGUI Class
 *
 * The UserGUI class serves as the entry point for the BoilerChat social media application and sets up the user
 * interface. It manages the connection to the server via a Client instance and maintains the currently logged-in user
 * as a static reference. The class uses a CardLayout to switch between different panels, starting with the LoginPanel
 * for user authentication and account creation. It initializes and displays the main application window (JFrame),
 * ensuring a consistent layout and proper handling of user interactions. The static methods provide centralized access
 * to the Client and User objects, enabling seamless communication and state management across the application.
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

public class UserGUI extends Thread implements UserGUIInterface {
    private static Client client = new Client("localhost", 4242);
    private static User user;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new UserGUI());
        client.accept();
    }

    @Override
    public void run() {
        JFrame frame = new JFrame("BoilerChat Social Media Application");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setResizable(false);

        CardLayout cardLayout = new CardLayout();
        JPanel mainPanel = new JPanel(cardLayout);

        // Initialize LoginPanel
        LoginPanel loginPanel = new LoginPanel(mainPanel, cardLayout);

        // Add panels
        mainPanel.add(loginPanel, "Login");

        frame.add(mainPanel);
        frame.setVisible(true);

        frame.revalidate();
        frame.repaint();
    }

    public static Client getClient() {
        return client;
    }

    public static void setUser(User user) {
        UserGUI.user = user;
    }

    public static User getUser() {
        return user;
    }
}
