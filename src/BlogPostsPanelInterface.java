import javax.swing.*;

/**
 * BlogPostPanelInterface Class
 * This interface represents all the method definitions for the BlogPostPanel class
 *
 * @author zaynrekhi
 * @author melody
 * @author srimadur
 * @author braydenbrafford
 * @author nothanlee
 * @version 1.0.0
 */

public interface BlogPostsPanelInterface {
    void toBlogPanel(User user);

    void toViewPostsPanel();

    void toFriendsPanel();

    JPanel createCommentPanel(Comment comment, Post post, JPanel commentsPanel);
}
