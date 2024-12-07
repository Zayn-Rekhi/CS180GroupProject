import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

public class ViewPostsPanel extends JPanel {
    private User loggedInUser;

    private JPanel mainPanel;
    private CardLayout cardLayout;

    public ViewPostsPanel(JPanel mainPanel, CardLayout cardLayout) {
        this.mainPanel = mainPanel;
        this.cardLayout = cardLayout;

        this.loggedInUser = UserGUI.getUser();

        setLayout(new BorderLayout());

        // Title at the top
        JLabel title = new JLabel("My Posts", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(title, BorderLayout.NORTH);


        // Center: Blog posts area
        JPanel postsPanel = new JPanel();
        postsPanel.setLayout(new BoxLayout(postsPanel, BoxLayout.Y_AXIS));

        ArrayList<Post> posts = UserGUI.getUser().getPosts();

        for (Post post : posts) {
            // Panel for individual post
            JPanel postPanel = new JPanel();
            postPanel.setLayout(new BorderLayout());
            postPanel.setBorder(BorderFactory.createTitledBorder(post.getTitle()));

            // Post description section
            JPanel postInfoPanel = new JPanel();
            postInfoPanel.setLayout(new BoxLayout(postInfoPanel, BoxLayout.Y_AXIS));
            postInfoPanel.add(new JLabel("<html><b>Description:</b> " + post.getDescription() + "</html>"));
            postInfoPanel.add(new JLabel("Created on: " + post.getDate()));
            postInfoPanel.add(new JLabel("Created by: " + post.getUser().getUserName()));

            JButton deleteBtn = new JButton("Delete");
            deleteBtn.addActionListener(e -> {
                DataTransfer data = new DataTransfer("POST DELETEPOST", post);
                DataTransfer resp = UserGUI.getClient().request(data);

                User out = (User) resp.getValue();

                posts.remove(post);

                UserGUI.setUser(out);

                toBlogPanel(UserGUI.getUser());
            });

            if (!post.getHidden()) {
                JButton hideBtn = new JButton("Hide Post");
                hideBtn.addActionListener(e -> {
                    DataTransfer data = new DataTransfer("POST HIDE", post);
                    DataTransfer resp = UserGUI.getClient().request(data);

                    Post out = (Post) resp.getValue();
                    User user = out.getUser();
                    UserGUI.setUser(user);

                    toBlogPanel(UserGUI.getUser());
                });

                postInfoPanel.add(hideBtn);

            } else {
                JButton showBtn = new JButton("Show Post");
                showBtn.addActionListener(e -> {
                    DataTransfer data = new DataTransfer("POST SHOWPOST", post);
                    DataTransfer resp = UserGUI.getClient().request(data);

                    User out = (User) resp.getValue();
                    UserGUI.setUser(out);

                    toBlogPanel(UserGUI.getUser());
                });

                postInfoPanel.add(showBtn);
            }

            postInfoPanel.add(deleteBtn);

            postPanel.add(postInfoPanel, BorderLayout.NORTH);

            JPanel commentsPanel = new JPanel();
            commentsPanel.setLayout(new BoxLayout(commentsPanel, BoxLayout.Y_AXIS));
            commentsPanel.setBorder(BorderFactory.createTitledBorder("Comments"));

            // Add existing comments
            for (Comment comment : post.getComments()) {
                commentsPanel.add(createCommentPanel(comment, post, commentsPanel));
            }

            // Scroll pane for comments
            JScrollPane commentsScrollPane = new JScrollPane(commentsPanel);
            commentsScrollPane.setPreferredSize(new Dimension(400, 150));
            postPanel.add(commentsScrollPane, BorderLayout.CENTER);

            // Add post panel to main posts panel
            postsPanel.add(postPanel);
        }

        JScrollPane scrollPane = new JScrollPane(postsPanel);
        add(scrollPane, BorderLayout.CENTER);

        // Bottom: Navigation buttons
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));


        JButton signOutButton = new JButton("Back");
        signOutButton.addActionListener(e -> toBlogPanel(UserGUI.getUser()));

        bottomPanel.add(signOutButton);

        add(bottomPanel, BorderLayout.SOUTH);
    }


    private JPanel createCommentPanel(Comment comment, Post post, JPanel commentsPanel) {
        JPanel commentPanel = new JPanel(new BorderLayout());

        // Display the comment text
        JLabel commentLabel = new JLabel("<html><b>" + comment.getCommenter().getUserName() + ":</b> " + comment.getMessage() + "</html>");
        commentPanel.add(commentLabel, BorderLayout.CENTER);

        // Buttons for interacting with the comment
        JPanel commentButtonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JLabel commentLikes = new JLabel("Likes: " + comment.getLikes());

        JButton likeCommentButton = new JButton("Like");
        likeCommentButton.addActionListener(e -> {

            DataTransfer params = new DataTransfer("COMMENT LIKECOMMENT", comment);
            DataTransfer response = UserGUI.getClient().request(params);

            System.out.println(response.getMessage());
            System.out.println(response.getValue());


            Comment c = (Comment) response.getValue();

            commentLikes.setText("Likes: " + c.getLikes());
        });

        JButton dislikeCommentButton = new JButton("Dislike");
        dislikeCommentButton.addActionListener(e -> {
            DataTransfer params = new DataTransfer("COMMENT DISLIKECOMMENT", comment);
            DataTransfer response = UserGUI.getClient().request(params);

            Comment c = (Comment) response.getValue();

            commentLikes.setText("Likes: " + c.getLikes());
        });

        JButton deleteCommentButton = new JButton("Delete");
        deleteCommentButton.addActionListener(e -> {
            if (comment.canDelete(UserGUI.getUser())) {
                ArrayList<Object> objs = new ArrayList<>();
                objs.add(comment);
                objs.add(UserGUI.getUser());

                DataTransfer params = new DataTransfer("COMMENT DELETECOMMENT", comment);
                DataTransfer response = UserGUI.getClient().request(params);

                post.getComments().remove(comment);
                commentsPanel.remove(commentPanel);
                commentsPanel.revalidate();
                commentsPanel.repaint();
            } else {
                JOptionPane.showMessageDialog(this, "You can only delete your own comments or if you're the post owner.");
            }
        });

        commentButtonsPanel.add(likeCommentButton);
        commentButtonsPanel.add(dislikeCommentButton);
        commentButtonsPanel.add(commentLikes);
        commentButtonsPanel.add(deleteCommentButton);

        commentPanel.add(commentButtonsPanel, BorderLayout.SOUTH);

        return commentPanel;
    }
    public void toBlogPanel(User user) {
        DataTransfer params = new DataTransfer("USER GETFRIENDSFEED", user);
        DataTransfer response = UserGUI.getClient().request(params);
        ArrayList<Post> posts = (ArrayList<Post>) response.getValue();
        UserGUI.setUser(user);

        mainPanel.add(new BlogPostsPanel(mainPanel, cardLayout, posts), "BlogPosts");
        mainPanel.revalidate();
        mainPanel.repaint();
        cardLayout.show(mainPanel, "BlogPosts");
    }
}
