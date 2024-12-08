import javax.swing.*;

public interface ViewPostsPanelInterface {
    void toViewPostsPanel();
    void toBlogPanel(User user);
    JPanel createCommentPanel(Comment comment, Post post, JPanel commentsPanel);
}
