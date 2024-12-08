import javax.swing.*;

/**
 * ViewPostsPanelInterface Class
 * This interface represents all the method definitions for the ViewPostsPanel class
 *
 * @author zaynrekhi
 * @author melody
 * @author srimadur
 * @author braydenbrafford
 * @author nothanlee
 * @version 1.0.0
 *
 * @version 1.0.0
 */
public interface ViewPostsPanelInterface {
    void toViewPostsPanel();
    void toBlogPanel(User user);
    JPanel createCommentPanel(Comment comment, Post post, JPanel commentsPanel);
}
