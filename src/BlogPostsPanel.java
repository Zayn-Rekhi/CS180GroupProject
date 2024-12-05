import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

public class BlogPostsPanel extends JPanel {
    private User loggedInUser;

    public BlogPostsPanel(JPanel mainPanel, CardLayout cardLayout, ArrayList<Post> posts, User loggedInUser) {
        this.loggedInUser = loggedInUser;

        setLayout(new BorderLayout());

        // Title at the top
        JLabel title = new JLabel("Social Media Feed", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(title, BorderLayout.NORTH);

        // Center: Blog posts area
        JPanel postsPanel = new JPanel();
        postsPanel.setLayout(new BoxLayout(postsPanel, BoxLayout.Y_AXIS));

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
            postPanel.add(postInfoPanel, BorderLayout.NORTH);

            // Comments section
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

            // Add comment button
            JButton addCommentButton = new JButton("Add Comment");
            addCommentButton.addActionListener(e -> {
                String commentText = JOptionPane.showInputDialog(this, "Enter your comment:");
                if (commentText != null && !commentText.trim().isEmpty()) {
                    Comment newComment = new Comment(loggedInUser, post, commentText, "12-05-2024");
                    post.addComment(newComment);

                    // Add the new comment dynamically to the comments panel
                    JPanel newCommentPanel = createCommentPanel(newComment, post, commentsPanel);
                    commentsPanel.add(newCommentPanel);
                    commentsPanel.revalidate();
                    commentsPanel.repaint();
                }
            });

            // Add button to post panel
            postPanel.add(addCommentButton, BorderLayout.SOUTH);

            // Likes and Dislikes section for the post
            JPanel likeDislikePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            JLabel likeCount = new JLabel("Likes: " + post.getLikes());
            JLabel dislikeCount = new JLabel("Dislikes: " + post.getDislikes());

            JButton likeButton = new JButton("Like");
            likeButton.addActionListener(e -> {
                post.addLike();
                likeCount.setText("Likes: " + post.getLikes());
            });

            JButton dislikeButton = new JButton("Dislike");
            dislikeButton.addActionListener(e -> {
                post.addDislike();
                dislikeCount.setText("Dislikes: " + post.getDislikes());
            });

            likeDislikePanel.add(likeButton);
            likeDislikePanel.add(dislikeButton);
            likeDislikePanel.add(likeCount);
            likeDislikePanel.add(dislikeCount);
            postPanel.add(likeDislikePanel, BorderLayout.SOUTH);

            // Add post panel to main posts panel
            postsPanel.add(postPanel);
        }

        JScrollPane scrollPane = new JScrollPane(postsPanel);
        add(scrollPane, BorderLayout.CENTER);

        // Bottom: Navigation buttons
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        JButton addFriendsButton = new JButton("Add Friends");
        addFriendsButton.addActionListener(e -> cardLayout.show(mainPanel, "Friends"));

        JButton settingsButton = new JButton("Settings");
        settingsButton.addActionListener(e -> cardLayout.show(mainPanel, "Settings"));

        JButton signOutButton = new JButton("Sign Out");
        signOutButton.addActionListener(e -> cardLayout.show(mainPanel, "Login"));

        bottomPanel.add(addFriendsButton);
        bottomPanel.add(settingsButton);
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
        JLabel commentDislikes = new JLabel("Dislikes: " + comment.getDislikes());

        JButton likeCommentButton = new JButton("Like");
        likeCommentButton.addActionListener(e -> {
            comment.addLike();
            commentLikes.setText("Likes: " + comment.getLikes());
        });

        JButton dislikeCommentButton = new JButton("Dislike");
        dislikeCommentButton.addActionListener(e -> {
            comment.addDislike();
            commentDislikes.setText("Dislikes: " + comment.getDislikes());
        });

        JButton deleteCommentButton = new JButton("Delete");
        deleteCommentButton.addActionListener(e -> {
            if (comment.canDelete(loggedInUser)) {
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
        commentButtonsPanel.add(commentDislikes);
        commentButtonsPanel.add(deleteCommentButton);

        commentPanel.add(commentButtonsPanel, BorderLayout.SOUTH);

        return commentPanel;
    }
}
