import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

public class BlogPostsPanel extends JPanel {
    private User loggedInUser;

    private JPanel mainPanel;
    private CardLayout cardLayout;

    public BlogPostsPanel(JPanel mainPanel, CardLayout cardLayout, ArrayList<Post> posts) {
        this.mainPanel = mainPanel;
        this.cardLayout = cardLayout;

        this.loggedInUser = UserGUI.getUser();

        setLayout(new BorderLayout());

        // Title at the top
        JLabel title = new JLabel("Social Media Feed", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(title, BorderLayout.NORTH);

        // JPanel create Post
        JPanel createPosts = new JPanel();
        createPosts.setLayout(new BoxLayout(createPosts, BoxLayout.Y_AXIS));

        createPosts.setAlignmentX(Component.CENTER_ALIGNMENT);
        createPosts.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        createPosts.setBorder(BorderFactory.createTitledBorder("Create Post"));

        createPosts.add(new Label("Enter Title: "));
        JTextField titlePost = new JTextField(15);
        createPosts.add(titlePost);

        createPosts.add(new Label("Enter Description: "));
        JTextField description = new JTextField(15);
        createPosts.add(description);

        createPosts.add(new Label("Enter Date: "));
        JTextField date = new JTextField(15);
        createPosts.add(date);

        JButton createPostBtn = new JButton("Create Post");
        createPostBtn.addActionListener(e -> {
            String titleStr = titlePost.getText();
            String descriptionStr = description.getText();
            String dateStr = date.getText();


            ArrayList<Object> values = new ArrayList<>();
            values.add(UserGUI.getUser());
            values.add(titleStr);
            values.add(descriptionStr);
            values.add(dateStr);

            DataTransfer params = new DataTransfer("POST CREATEPOST", values);
            DataTransfer response = UserGUI.getClient().request(params);

            loggedInUser = (User) response.getValue();
            UserGUI.setUser(loggedInUser);

            JOptionPane.showMessageDialog(null, "Post Created");
        });

        createPosts.add(createPostBtn);

        add(createPosts, BorderLayout.NORTH);



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
            postInfoPanel.add(new JLabel("Created by: " + post.getUser().getUserName()));
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
                DataTransfer params = new DataTransfer("POST LIKEPOST", post);
                DataTransfer response = UserGUI.getClient().request(params);

                Post p = (Post) response.getValue();
                System.out.println(p.getLikes());

                for (int i = 0; i < posts.size(); i++) {
                    if (posts.get(i).getPostID() == p.getPostID()) {
                        posts.set(i, p);
                    }
                }

                likeCount.setText("Likes: " + post.getLikes());
            });

            JButton dislikeButton = new JButton("Dislike");
            dislikeButton.addActionListener(e -> {
                DataTransfer params = new DataTransfer("POST DISLIKEPOST", post);
                DataTransfer response = UserGUI.getClient().request(params);

                Post p = (Post) response.getValue();
                dislikeCount.setText("Dislikes: " + p.getDislikes());
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
        addFriendsButton.addActionListener(e -> {
            toFriendsPanel();
        });

        JButton signOutButton = new JButton("Sign Out");
        signOutButton.addActionListener(e -> cardLayout.show(mainPanel, "Login"));

        bottomPanel.add(addFriendsButton);
        bottomPanel.add(signOutButton);

        add(bottomPanel, BorderLayout.SOUTH);
    }

    private JPanel createPostPanel(Post post) {
        JPanel postPanel = new JPanel(new BorderLayout());

        return postPanel;
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

    public void toRefreshBlog(User user) {
        DataTransfer params = new DataTransfer("USER GETFRIENDSFEED", user);
        DataTransfer response = UserGUI.getClient().request(params);
        ArrayList<Post> posts = (ArrayList<Post>) response.getValue();
        UserGUI.setUser(user);

        System.out.println(user.getFriends().get(2).getUserName());
        System.out.println(user.getFriends().get(2).getPosts());
        System.out.println(user.getFriends());
        System.out.println(posts);
//        posts = UserGUI.fetchPostsForUser();

        mainPanel.add(new BlogPostsPanel(mainPanel, cardLayout, posts), "BlogPosts");
        mainPanel.revalidate();
        mainPanel.repaint();
        cardLayout.show(mainPanel, "BlogPosts");
    }

    public void toFriendsPanel() {
        mainPanel.add(new FriendsPanel(mainPanel, cardLayout), "FriendsPanel");
        mainPanel.revalidate();
        mainPanel.repaint();
        cardLayout.show(mainPanel, "FriendsPanel");
    }
}
