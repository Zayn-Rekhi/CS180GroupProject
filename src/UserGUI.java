import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;


public class UserGUI extends Thread {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new UserGUI());
    }

    @Override
    public void run() {
        JFrame frame = new JFrame("Social Media Application");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setResizable(false);

        CardLayout cardLayout = new CardLayout();
        JPanel mainPanel = new JPanel(cardLayout);

        // Initialize LoginPanel
        LoginPanel loginPanel = new LoginPanel(mainPanel, cardLayout);

        // Placeholder for posts (replace with actual logic)
        ArrayList<Post> posts = fetchPostsForUser();

        // Add panels
        mainPanel.add(loginPanel, "Login");
        mainPanel.add(new BlogPostsPanel(mainPanel, cardLayout, posts, loginPanel.getLoggedInUser()), "BlogPosts");

        frame.add(mainPanel);
        frame.setVisible(true);
    }

    // Placeholder method to fetch posts
    private ArrayList<Post> fetchPostsForUser() {
        ArrayList<Post> posts = new ArrayList<>();
        User user = new User("SampleUser", "password123", "Sample bio!");

        try {
            posts.add(new Post(user, "Post 1", "First post description.", "12-01-2024"));
            posts.add(new Post(user, "Post 2", "Second post description.", "12-02-2024"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return posts;
    }
}
