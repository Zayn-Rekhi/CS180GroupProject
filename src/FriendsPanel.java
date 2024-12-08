import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class FriendsPanel extends JPanel implements FriendsPanelInterface {
    private User loggedInUser;

    private JPanel mainPanel;
    private CardLayout cardLayout;


    public FriendsPanel(JPanel mainPanel, CardLayout cardLayout) {
        this.mainPanel = mainPanel;
        this.cardLayout = cardLayout;

        this.loggedInUser = UserGUI.getUser();

        setLayout(new BorderLayout());

        // Title
        JLabel title = new JLabel("Manage Friends", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(title, BorderLayout.NORTH);

        // Friends list area (placeholder)



        JPanel addFriendPanel = new JPanel();
        addFriendPanel.setLayout(new BoxLayout(addFriendPanel, BoxLayout.X_AXIS));

        JLabel search = new JLabel("Search for Users: ");
        JTextField searchField = new JTextField(15);
        JButton searchButton = new JButton("Search");

        searchButton.addActionListener(e -> {
            if (searchField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter your search term");
            } else {
                DataTransfer data = new DataTransfer("USER SEARCH", searchField.getText());
                DataTransfer request = UserGUI.getClient().request(data);

                System.out.println(request.getMessage());
                System.out.println(request.getValue());

                if (request.getMessage().equals("SUCCESS")) {
                    User out = (User) request.getValue();
                    String display = String.format("Username: %s\nBio: %s\n", out.getUserName(), out.getBio());

                    if (loggedInUser.isFriendOf(out)) {
                        display += "CURRENTLY FRIENDS :)\n";
                    }

                    if (loggedInUser.hasBlocked(out)) {
                        display += "CURRENTLY BLOCKED\n";
                    }

                    Object[] options = { "OK", "Friend", "Remove", "Block", "Unblock"};
                    Object selection = JOptionPane.showOptionDialog(null, display, "Profile Description",
                            JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
                            null, options, options[0]);

                    String selec = (String) options[(int) selection];

                    DataTransfer resp = null;
                    if (selec.equals("Friend")) {
                        ArrayList<User> obj = new ArrayList<User>();
                        obj.add(loggedInUser);
                        obj.add(out);

                        DataTransfer dataFriend = new DataTransfer("USER ADDFRIEND", obj);
                        resp = UserGUI.getClient().request(dataFriend);

                        UserGUI.setUser((User) resp.getValue());
                    }

                    if (selec.equals("Remove")) {
                        ArrayList<User> obj = new ArrayList<User>();
                        obj.add(loggedInUser);
                        obj.add(out);

                        DataTransfer dataFriend = new DataTransfer("USER REMOVEFRIEND", obj);
                        resp = UserGUI.getClient().request(dataFriend);

                        UserGUI.setUser((User) resp.getValue());
                    }

                    if (selec.equals("Block")) {
                        ArrayList<User> obj = new ArrayList<User>();
                        obj.add(loggedInUser);
                        obj.add(out);

                        DataTransfer dataFriend = new DataTransfer("USER BLOCKFRIEND", obj);
                        resp = UserGUI.getClient().request(dataFriend);

                        UserGUI.setUser((User) resp.getValue());
                    }

                    if (selec.equals("Unblock")) {
                        ArrayList<User> obj = new ArrayList<User>();
                        obj.add(loggedInUser);
                        obj.add(out);

                        DataTransfer dataFriend = new DataTransfer("USER UNBLOCKFRIEND", obj);
                        resp = UserGUI.getClient().request(dataFriend);

                        UserGUI.setUser((User) resp.getValue());
                    }

                    if (resp != null && !selec.equals("OK")) {
                        JOptionPane.showMessageDialog(null, "Success!", null, JOptionPane.INFORMATION_MESSAGE);
                        loggedInUser = (User) resp.getValue();
                    } else if (!selec.equals("OK")) {
                        JOptionPane.showMessageDialog(null, "Error", "Error", JOptionPane.ERROR_MESSAGE);
                    }

                } else {
                    JOptionPane.showMessageDialog(null, "User could not be found!", null, JOptionPane.ERROR_MESSAGE);
                }

            }

            toBlogPanel(UserGUI.getUser());
        });

        addFriendPanel.add(search);
        addFriendPanel.add(searchField);
        addFriendPanel.add(searchButton);

        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new FlowLayout());
        searchPanel.add(addFriendPanel);

        add(searchPanel, BorderLayout.NORTH);

        JPanel friendsListPanel = new JPanel();
        friendsListPanel.setLayout(new BoxLayout(friendsListPanel, BoxLayout.Y_AXIS));
        friendsListPanel.setBorder(BorderFactory.createTitledBorder("Friends and Blocked Users"));

        ArrayList<User> friends = loggedInUser.getFriends();

        for (User friend : friends) {
            JLabel friendLabel = new JLabel("Friend: " + friend.getUserName());
            JLabel friendDescription = new JLabel("Bio: " + friend.getBio());

            friendLabel.setForeground(Color.GREEN);
            friendLabel.setFont(new Font("Arial", Font.BOLD, 16));
            friendDescription.setFont(new Font("Arial", Font.BOLD, 10));

            friendsListPanel.add(friendLabel);
            friendsListPanel.add(friendDescription);
        }

        ArrayList<User> blocked = loggedInUser.getBlocked();

        for (User block : blocked) {
            JLabel friendLabel = new JLabel("Blocked: " + block.getUserName());
            JLabel friendDescription = new JLabel("Bio: " + block.getBio());

            friendLabel.setForeground(Color.RED);
            friendLabel.setFont(new Font("Arial", Font.BOLD, 16));
            friendDescription.setFont(new Font("Arial", Font.BOLD, 10));

            friendsListPanel.add(friendLabel);
            friendsListPanel.add(friendDescription);
        }

        JScrollPane scrollPane = new JScrollPane(friendsListPanel);
        add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();

        JButton backButton = new JButton("Back to Feed");
        backButton.addActionListener(e -> toBlogPanel(loggedInUser));
        bottomPanel.add(backButton);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    public void toBlogPanel(User user) {
        UserGUI.setUser(user);

        DataTransfer params = new DataTransfer("USER GETFRIENDSFEED", user);
        DataTransfer response = UserGUI.getClient().request(params);
        ArrayList<Post> posts = (ArrayList<Post>) response.getValue();

        mainPanel.add(new BlogPostsPanel(mainPanel, cardLayout, posts), "BlogPosts");
        mainPanel.revalidate();
        mainPanel.repaint();
        cardLayout.show(mainPanel, "BlogPosts");
    }
}
