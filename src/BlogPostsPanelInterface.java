import javax.swing.*;

public interface BlogPostsPanelInterface {
    void toBlogPanel(User user);
    void toViewPostsPanel();
    void toFriendsPanel();
    JPanel createCommentPanel(Comment comment, Post post, JPanel commentsPanel);
}
