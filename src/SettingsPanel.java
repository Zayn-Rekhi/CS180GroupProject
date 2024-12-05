import java.awt.*;
import javax.swing.*;

public class SettingsPanel extends JPanel {
    public SettingsPanel(JPanel mainPanel, CardLayout cardLayout) {
        setLayout(new BorderLayout());

        // Title
        JLabel title = new JLabel("Settings", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(title, BorderLayout.NORTH);

        // Settings options (placeholder)
        JPanel settingsOptionsPanel = new JPanel();
        settingsOptionsPanel.setLayout(new BoxLayout(settingsOptionsPanel, BoxLayout.Y_AXIS));
        settingsOptionsPanel.setBorder(BorderFactory.createTitledBorder("User Preferences"));

        // Placeholder options
        JCheckBox notificationsCheckBox = new JCheckBox("Enable Notifications");
        JCheckBox darkModeCheckBox = new JCheckBox("Enable Dark Mode");
        settingsOptionsPanel.add(notificationsCheckBox);
        settingsOptionsPanel.add(darkModeCheckBox);

        JScrollPane scrollPane = new JScrollPane(settingsOptionsPanel);
        add(scrollPane, BorderLayout.CENTER);

        // Back button
        JButton backButton = new JButton("Back to Feed");
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "BlogPosts"));
        JPanel bottomPanel = new JPanel();
        bottomPanel.add(backButton);
        add(bottomPanel, BorderLayout.SOUTH);
    }
}
