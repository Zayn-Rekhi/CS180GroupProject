import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;


public class UserGUI extends Thread {
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

//        ArrayList<Post> posts;
//
//        if (user != null) {
//            DataTransfer params = new DataTransfer("USER GETFRIENDSFEED", user);
//            DataTransfer response = client.request(params);
//            posts = (ArrayList<Post>) response.getValue();
//        } else {
//            posts = fetchPostsForUser();
//        }

        // Add panels
        mainPanel.add(loginPanel, "Login");

        frame.add(mainPanel);
        frame.setVisible(true);

        frame.revalidate();
        frame.repaint();
    }

    // Placeholder method to fetch posts
    public static ArrayList<Post> fetchPostsForUser() {
        ArrayList<Post> posts = new ArrayList<>();
        User user = new User("SampleUser", "password123", "Sample bio!");

        try {
            ArrayList<Comment> comments = new ArrayList<>();


            posts.add(new Post(user, "Post 1", "First post description.", "12-01-2024"));
            posts.add(new Post(user, "Post 2", "Second post description.", "12-02-2024"));

            posts.get(0).addComment(new Comment(user, posts.get(0), "Hello", "12-01-2024"));

        } catch (Exception e) {
            e.printStackTrace();
        }

        return posts;
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
