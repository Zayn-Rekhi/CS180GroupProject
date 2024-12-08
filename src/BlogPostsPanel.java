import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;


/**
 * BlogPostPanel Class
 * The BlogPostsPanel class is a user interface component for a social media feed, built using Java Swing.
 * It allows users to create new posts, view a feed of existing posts, and interact with them through likes, dislikes,
 * and comments. Each post displays its details (title, description, author, date) and has a dedicated section for
 * comments, which can be dynamically added, liked, or deleted. The panel integrates navigation features, enabling
 * users to switch to other sections like viewing their own posts or adding friends. It communicates with a server
 * using DataTransfer objects to manage data updates and ensure real-time synchronization of interactions.
 *
 * @author zaynrekhi
 * @author melody
 * @author srimadur
 * @author braydenbrafford
 * @author nothanlee
 * @version 1.0.0
 */

public class BlogPostsPanel extends JPanel implements BlogPostsPanelInterface {

    private JPanel mainPanel;
    private CardLayout cardLayout;
    private ArrayList<Post> posts;

    public BlogPostsPanel(JPanel mainPanel, CardLayout cardLayout, ArrayList<Post> posts) {
        this.mainPanel = mainPanel;
        this.cardLayout = cardLayout;
        this.posts = posts;

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


        JPanel buttonPosts = new JPanel();
        buttonPosts.setLayout(new BoxLayout(buttonPosts, BoxLayout.X_AXIS));

        JButton createPostBtn = new JButton("Create Post");
        createPostBtn.addActionListener(e -> {
            String titleStr = titlePost.getText();
            String descriptionStr = description.getText();
            SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");
            // Format the current date
            String dateStr = formatter.format(new Date());


            if (titleStr.isEmpty() || descriptionStr.isEmpty() || dateStr.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Check Post Values");
            }

            ArrayList<Object> values = new ArrayList<>();
            values.add(UserGUI.getUser());
            values.add(titleStr);
            values.add(descriptionStr);
            values.add(dateStr);

            DataTransfer params = new DataTransfer("POST CREATEPOST", values);
            DataTransfer response = UserGUI.getClient().request(params);

            Post resp = (Post) response.getValue();
            UserGUI.setUser(resp.getUser());

            if (response.getMessage().equals("SUCCESS"))
                JOptionPane.showMessageDialog(null, "Post Created");
            else
                JOptionPane.showMessageDialog(null, "Post Creation Failed");
        });


        buttonPosts.add(createPostBtn);
        createPosts.add(buttonPosts);

        add(createPosts, BorderLayout.NORTH);


        // Center: Blog posts area
        JPanel postsPanel = new JPanel();
        postsPanel.setLayout(new BoxLayout(postsPanel, BoxLayout.Y_AXIS));

        HashMap<Post, Post> postMap = new HashMap<Post, Post>();

        for (int x = 0; x < this.posts.size(); x++) {
            Post post = this.posts.get(x);

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
                    // Define the desired format
                    SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");
                    // Format the current date
                    String formattedDate = formatter.format(new Date());

                    ArrayList<Object> commentInfo = new ArrayList<>();
                    commentInfo.add(UserGUI.getUser());
                    commentInfo.add(post);
                    commentInfo.add(commentText);
                    commentInfo.add(formattedDate);

                    DataTransfer data = new DataTransfer("COMMENT CREATECOMMENT", commentInfo);
                    DataTransfer response = UserGUI.getClient().request(data);

                    Comment newComment = (Comment) response.getValue();

                    UserGUI.setUser(newComment.getCommenter());

                    // Add the new comment dynamically to the comments panel
                    JPanel newCommentPanel = createCommentPanel(newComment, post, commentsPanel);
                    commentsPanel.add(newCommentPanel);
                    commentsPanel.revalidate();
                    commentsPanel.repaint();
                }
            });

            // Add button to post panel

            // Likes and Dislikes section for the post
            JPanel likeDislikePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            JLabel likeCount = new JLabel("Likes: " + post.getLikes());

            JButton dislikeButton = new JButton("Dislike");
            JButton likeButton = new JButton("Like");

            likeButton.addActionListener(e -> {
                ArrayList<Object> postObjs = new ArrayList<>();
                postObjs.add(post);
                postObjs.add(UserGUI.getUser());


                DataTransfer params = new DataTransfer("POST LIKEPOST", postObjs);
                DataTransfer response = UserGUI.getClient().request(params);

                Post p = (Post) response.getValue();

                if (p == null) {
                    response = UserGUI.getClient().request(params);
                    p = (Post) response.getValue();
                }
                System.out.println(p.getLikes());

                for (int i = 0; i < posts.size(); i++) {
                    if (posts.get(i).getTitle().equals(p.getTitle())) {
                        posts.set(i, p);
                    }
                }

                User user = UserGUI.getUser();
//                user.setPosts(posts);

                toBlogPanel(user);

                likeCount.setText("Likes: " + p.getLikes());
            });

            dislikeButton.addActionListener(e -> {
                ArrayList<Object> postObjs = new ArrayList<>();
                postObjs.add(post);
                postObjs.add(UserGUI.getUser());

                DataTransfer params = new DataTransfer("POST UNLIKEPOST", postObjs);
                DataTransfer response = UserGUI.getClient().request(params);

                System.out.println(response.getMessage());
                System.out.println(response.getValue());

                Post p = (Post) response.getValue();
                if (p == null) {
                    response = UserGUI.getClient().request(params);
                    p = (Post) response.getValue();
                }

                for (int i = 0; i < posts.size(); i++) {
                    if (posts.get(i).getTitle().equals(p.getTitle())) {
                        posts.set(i, p);
                    }
                }

                User user = UserGUI.getUser();

                toBlogPanel(user);

                likeCount.setText("Likes: " + p.getLikes());
            });

            if (!post.checkLiked(UserGUI.getUser())) {
                likeButton.setEnabled(true);
                dislikeButton.setEnabled(false);
            } else {
                likeButton.setEnabled(false);
                dislikeButton.setEnabled(true);
            }

            likeDislikePanel.add(likeButton);
            likeDislikePanel.add(dislikeButton);
            likeDislikePanel.add(likeCount);
            likeDislikePanel.add(addCommentButton);

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

        JButton viewMyPostsBtn = new JButton("View My Posts");
        viewMyPostsBtn.addActionListener(e -> {
            toViewPostsPanel();
        });

        JButton signOutButton = new JButton("Sign Out");
        signOutButton.addActionListener(e -> cardLayout.show(mainPanel, "Login"));

        bottomPanel.add(addFriendsButton);
        bottomPanel.add(viewMyPostsBtn);
        bottomPanel.add(signOutButton);

        add(bottomPanel, BorderLayout.SOUTH);
    }


    public JPanel createCommentPanel(Comment comment, Post post, JPanel commentsPanel) {
        JPanel commentPanel = new JPanel(new BorderLayout());

        // Display the comment text
        JLabel commentLabel = new JLabel("<html><b>" + comment.getCommenter().getUserName() + ":</b> " + comment.getMessage() + "</html>");
        commentPanel.add(commentLabel, BorderLayout.CENTER);

        // Buttons for interacting with the comment
        JPanel commentButtonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JLabel commentLikes = new JLabel("Likes: " + comment.getLikes());

        JButton likeCommentButton = new JButton("Like");
        likeCommentButton.addActionListener(e -> {
            ArrayList<Object> commentObjs = new ArrayList<>();
            commentObjs.add(comment);
            commentObjs.add(UserGUI.getUser());

            DataTransfer params = new DataTransfer("COMMENT LIKECOMMENT", commentObjs);
            DataTransfer response = UserGUI.getClient().request(params);

            System.out.println(response.getMessage());
            System.out.println(response.getValue());

            Comment c = (Comment) response.getValue();

            toBlogPanel(UserGUI.getUser());
            commentLikes.setText("Likes: " + c.getLikes());
        });

        JButton dislikeCommentButton = new JButton("Dislike");
        dislikeCommentButton.addActionListener(e -> {
            ArrayList<Object> commentObjs = new ArrayList<>();
            commentObjs.add(comment);
            commentObjs.add(UserGUI.getUser());

            DataTransfer params = new DataTransfer("COMMENT DISLIKECOMMENT", commentObjs);
            DataTransfer response = UserGUI.getClient().request(params);

            Comment c = (Comment) response.getValue();

            toBlogPanel(UserGUI.getUser());
            commentLikes.setText("Likes: " + c.getLikes());
        });

        if (comment.canDelete(UserGUI.getUser())) {

            JButton deleteCommentButton = new JButton("Delete");
            deleteCommentButton.addActionListener(e -> {
                if (comment.canDelete(UserGUI.getUser())) {
                    ArrayList<Object> objs = new ArrayList<>();
                    objs.add(comment);
                    objs.add(UserGUI.getUser());

                    DataTransfer params = new DataTransfer("COMMENT DELETECOMMENT", objs);
                    DataTransfer response = UserGUI.getClient().request(params);

                    System.out.println(response.getMessage());
                    System.out.println(((Comment) response.getValue()).getPost());

                    post.getComments().remove(comment);

                    for (int i = 0; i < posts.size(); i++) {
                        if (posts.get(i).getTitle().equals(post.getTitle())) {
                            posts.set(i, post);
                        }
                    }

                    commentsPanel.remove(commentPanel);
                    commentsPanel.revalidate();
                    commentsPanel.repaint();
                } else {
                    String msg = "You can only delete your own comments or if you're the post owner.";
                    JOptionPane.showMessageDialog(this, msg);
                }
            });

            commentButtonsPanel.add(deleteCommentButton);
        }


        if (!comment.checkLiked(UserGUI.getUser())) {
            likeCommentButton.setEnabled(true);
            dislikeCommentButton.setEnabled(false);
        } else {
            likeCommentButton.setEnabled(false);
            dislikeCommentButton.setEnabled(true);
        }


        commentButtonsPanel.add(likeCommentButton);
        commentButtonsPanel.add(dislikeCommentButton);
        commentButtonsPanel.add(commentLikes);

        commentPanel.add(commentButtonsPanel, BorderLayout.SOUTH);

        return commentPanel;
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

    public void toViewPostsPanel() {
        mainPanel.add(new ViewPostsPanel(mainPanel, cardLayout), "ViewPostsPanel");
        mainPanel.revalidate();
        mainPanel.repaint();
        cardLayout.show(mainPanel, "ViewPostsPanel");
    }


    public void toFriendsPanel() {
        mainPanel.add(new FriendsPanel(mainPanel, cardLayout), "FriendsPanel");
        mainPanel.revalidate();
        mainPanel.repaint();
        cardLayout.show(mainPanel, "FriendsPanel");
    }
}
