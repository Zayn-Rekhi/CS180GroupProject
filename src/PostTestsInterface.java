/**
 * Post Tests Interface
 * contains all the methods to be implemented in the Post Tests class
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
public interface PostTestsInterface {
    void setUp();
    void testInitialization();
    void testMutators();
    void testPostWithoutIMGConstructor();
    void testAddAndRemoveLike();
    void testAddAndRemoveDislike();
    void testDisplay();
    void testComment();
    void testDisplayWithComment();
    void testRemoveComment();
}
