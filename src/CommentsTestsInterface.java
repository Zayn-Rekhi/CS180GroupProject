/**
 * Comment Tests Interface
 * contains all the methods to be implemented in the Comment Tests class
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
public interface CommentsTestsInterface {
    void setUp();
    void testAddLikeAndDisLikeComment();
    void testGetterAndSetter();
    void testEditMessage();
    void testDisplayedComment();
    void testDeleteCommentbyCommenter();
    void testDeleteCommentByPostOwner();
    void testDeleteCommentByUnauthorizedUser();
}
