import java.awt.*;
import javax.swing.*;

public class FriendsPanel extends JPanel {
    public FriendsPanel(JPanel mainPanel, CardLayout cardLayout) {
        setLayout(new BorderLayout());

        // Title
        JLabel title = new JLabel("Manage Friends", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(title, BorderLayout.NORTH);

        // Friends list area (placeholder)
        JPanel friendsListPanel = new JPanel();
        friendsListPanel.setLayout(new BoxLayout(friendsListPanel, BoxLayout.Y_AXIS));
        friendsListPanel.setBorder(BorderFactory.createTitledBorder("Your Friends"));

        // Placeholder for friends
        for (int i = 1; i <= 5; i++) {
            JLabel friendLabel = new JLabel("Friend " + i);
            friendsListPanel.add(friendLabel);
        }

        JScrollPane scrollPane = new JScrollPane(friendsListPanel);
        add(scrollPane, BorderLayout.CENTER);

        // Back button
        JButton backButton = new JButton("Back to Feed");
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "BlogPosts"));
        JPanel bottomPanel = new JPanel();
        bottomPanel.add(backButton);
        add(bottomPanel, BorderLayout.SOUTH);
    }
}
