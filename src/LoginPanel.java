import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

public class LoginPanel extends JPanel implements LoginPanelInterface {

    private JPanel mainPanel;
    private CardLayout cardLayout;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JTextField bioField; // For creating a new user

    public LoginPanel(JPanel mainPanel, CardLayout cardLayout) {
        this.mainPanel = mainPanel;
        this.cardLayout = cardLayout;

        setLayout(new BorderLayout());

        // Title
        JLabel title = new JLabel("Welcome to BoilerChat!", SwingConstants.CENTER);
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

            ArrayList<String> out = new ArrayList<String>();
            out.add(username);
            out.add(password);

            DataTransfer params = new DataTransfer("USER GETLOGIN", out);
            DataTransfer response = UserGUI.getClient().request(params);

            System.out.println(response.getMessage());
            System.out.println(response.getValue());


            boolean valid = !username.isEmpty() && !password.isEmpty();

            if (valid && !response.getMessage().equals("FAILURE")) {
                User loggedInUser = (User) response.getValue();
                UserGUI.setUser(loggedInUser);

                System.out.println("Login successful! Logged in as: " + loggedInUser.getUserName());
                cardLayout.show(mainPanel, "BlogPosts");

                toBlogPanel(loggedInUser);
            } else {
                JOptionPane.showMessageDialog(this, "Invalid username or password!");
            }
        });

        // Create User button logic
        createUserButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            String bio = bioField.getText();

            ArrayList<String> out = new ArrayList<String>();
            out.add(username);
            out.add(password);
            out.add(bio);



            boolean valid = !username.isEmpty() && !password.isEmpty() && !bio.isEmpty();

            if (valid) {
                DataTransfer params = new DataTransfer("USER CREATENEWUSER", out);
                DataTransfer response = UserGUI.getClient().request(params);

                User loggedInUser = (User) response.getValue();

                UserGUI.setUser(loggedInUser);
                System.out.println("User created successfully! Logged in as: " + loggedInUser.getUserName());

                toBlogPanel(loggedInUser);
            } else {
                JOptionPane.showMessageDialog(this, "All fields are required to create a user!");
            }
        });
    }

    public void toBlogPanel(User user) {
        DataTransfer params = new DataTransfer("USER GETFRIENDSFEED", user);
        DataTransfer response = UserGUI.getClient().request(params);
        ArrayList<Post> posts = (ArrayList<Post>) response.getValue();
        UserGUI.setUser(user);

        for (Post post : posts) {
            for (Comment comment : post.getComments()) {
                System.out.println(comment);
            }
        }

        mainPanel.add(new BlogPostsPanel(mainPanel, cardLayout, posts), "BlogPosts");
        mainPanel.revalidate();
        mainPanel.repaint();
        cardLayout.show(mainPanel, "BlogPosts");
    }
}
