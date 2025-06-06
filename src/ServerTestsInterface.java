/**
 * Server Tests Interface
 * contains all the methods to be implemented in the Server Tests class
 *
 * @author zaynrekhi
 * @author melody
 * @author srimadur
 * @author braydenbrafford
 * @author nothanlee
 * @version 1.0.0
 */
public interface ServerTestsInterface {
    void createUserZaynExample();

    void createUserSriExample();

    void createUserBrandonExample();

    void testCreateUsers();

    void testCreateUsersInvalidCredentials();

    void testGetLogin();

    void testRemoveUser();

    void testAddFriend();

    void testRemoveFriend();

    void testBlockFriend();

    void testUnBlockFriend();

    void testCreatePost();

    void testDeletePost();

    void testLikePost();

    void testDislikePost();

    void testFindFriendsPosts();

    void testCreateComment();

    void testDeleteComment();

    void testLikeComment();

    void testDislikeComment();

    void testShowPost();

    void testHidePost();
}
