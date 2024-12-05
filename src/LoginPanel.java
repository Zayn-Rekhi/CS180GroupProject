import java.awt.*;
import javax.swing.*;

public class LoginPanel extends JPanel {
    private JPanel mainPanel;
    private CardLayout cardLayout;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JTextField bioField; // For creating a new user
    private User loggedInUser;

    public LoginPanel(JPanel mainPanel, CardLayout cardLayout) {
        this.mainPanel = mainPanel;
        this.cardLayout = cardLayout;

        setLayout(new BorderLayout());

        // Title
        JLabel title = new JLabel("Social Media Application", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 18));
        title.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        add(title, BorderLayout.NORTH);

        // Center: Login and Create User form
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        // Username label and text field
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Username:"), gbc);

        gbc.gridx = 1;
        usernameField = new JTextField(15);
        formPanel.add(usernameField, gbc);

        // Password label and text field
        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(new JLabel("Password:"), gbc);

        gbc.gridx = 1;
        passwordField = new JPasswordField(15);
        formPanel.add(passwordField, gbc);

        // Bio label and text field for creating a new user
        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(new JLabel("Bio:"), gbc);

        gbc.gridx = 1;
        bioField = new JTextField(15);
        formPanel.add(bioField, gbc);

        add(formPanel, BorderLayout.CENTER);

        // Bottom: Login and Create User buttons
        JPanel buttonPanel = new JPanel();
        JButton loginButton = new JButton("Login");
        loginButton.setPreferredSize(new Dimension(100, 30));

        JButton createUserButton = new JButton("Create User");
        createUserButton.setPreferredSize(new Dimension(120, 30));

        buttonPanel.add(loginButton);
        buttonPanel.add(createUserButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Login button logic
        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            // Simulate login logic (replace with actual logic)
            if (username.equals("SampleUser") && password.equals("password123")) {
                loggedInUser = new User(username, password, "This is a dynamic bio!"); // Replace bio dynamically
                System.out.println("Login successful! Logged in as: " + loggedInUser.getUserName());
                cardLayout.show(mainPanel, "BlogPosts");
            } else {
                JOptionPane.showMessageDialog(this, "Invalid username or password!");
            }
        });

        // Create User button logic
        createUserButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            String bio = bioField.getText();

            // Simulate create user logic (replace with actual logic)
            if (!username.isEmpty() && !password.isEmpty() && !bio.isEmpty()) {
                loggedInUser = new User(username, password, bio); // Create a new user
                System.out.println("User created successfully! Logged in as: " + loggedInUser.getUserName());
                cardLayout.show(mainPanel, "BlogPosts");
            } else {
                JOptionPane.showMessageDialog(this, "All fields are required to create a user!");
            }
        });
    }

    public User getLoggedInUser() {
        return loggedInUser;
    }
}
