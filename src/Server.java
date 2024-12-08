import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Server Class
 * This class is important, it handles all of the commands and stores the database for the platform.
 * There is only one Server and it takes a command
 * from a DataTransfer object that will be linked to a buttons implemented later
 * These are then used to make changes to the database.
 * There are methods that handle the processing of these commands as they relate to posts, users, or comments.
 * It extends Thread, making it runnable, and implements ServerInterface to document its methods.
 *
 * @author zaynrekhi
 * @author melody
 * @author srimadur
 * @author braydenbrafford
 * @author nothanlee
 * @version 1.0.0
 */
public class Server extends Thread implements ServerInterface {
    private Socket clientSocket;
    private Database database = new Database(); // Make it get an empty database later

    public Server(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public DataTransfer processUser(String command, DataTransfer data) {
        if (command.equals("GETLOGIN")) {
            ArrayList<String> values = (ArrayList<String>) data.getValue();
            String username = values.get(0);
            String password = values.get(1);

            if (database.checkLogin(username, password)) {
                User user = database.findUser(username);
                return new DataTransfer("SUCCESS", user);
            } else {
                return new DataTransfer("FAILURE", "Invalid username or password");
            }
        }

        if (command.equals("CREATENEWUSER")) {
            ArrayList<String> values = (ArrayList<String>) data.getValue();
            String username = values.get(0);
            String password = values.get(1);
            String bio = values.get(2);

            boolean valid = database.createUser(username, password, bio);

            if (valid) {
                return new DataTransfer("SUCCESS", database.findUser(username));
            } else {
                String msg = "User Already Exists or Credentials are Invalid";
                return new DataTransfer("FAILURE", msg);
            }
        }

        if (command.equals("DELETEUSER")) {
            User value = (User) data.getValue();
            boolean success = database.removeUser(value);

            if (success) {
                return new DataTransfer("SUCCESS", value);
            } else {
                return new DataTransfer("FAILURE", "User not found");
            }
        }

        if (command.equals("MODIFYUSER")) {
            User value = (User) data.getValue();
            User current = database.findUser(value.getUserName());
            boolean success = database.modifyUser(current, value);

            if (success) {
                return new DataTransfer("SUCCESS", value);
            } else {
                return new DataTransfer("FAILURE", "User Could Not Be Modified");
            }
        }

        if (command.equals("ADDFRIEND")) {
            var values = (ArrayList<User>) data.getValue();
            User primary = values.get(0);
            User friend = values.get(1);

            primary.addFriend(friend);

            User prev = database.findUser(primary.getUserName());
            boolean success = database.modifyUser(primary, prev);
            User newUser = database.findUser(primary.getUserName());
            
            if (success) {
                return new DataTransfer("SUCCESS", newUser);
            } else {
                return new DataTransfer("FAILURE", "User Could Not Be Modified");
            }
        }

        if (command.equals("REMOVEFRIEND")) {
            var values = (ArrayList<User>) data.getValue();
            User primary = values.get(0);
            User friend = values.get(1);

            primary.removeFriend(friend);
            User prev = database.findUser(primary.getUserName());
            boolean success = database.modifyUser(primary, prev);

            if (success) {
                return new DataTransfer("SUCCESS", primary);
            } else {
                return new DataTransfer("FAILURE", "User Could Not Be Modified");
            }
        }

        if (command.equals("BLOCKFRIEND")) {
            var values = (ArrayList<User>) data.getValue();
            User primary = values.get(0);
            User friend = values.get(1);

            primary.block(friend);
            User prev = database.findUser(primary.getUserName());
            boolean success = database.modifyUser(primary, prev);

            if (success) {
                return new DataTransfer("SUCCESS", primary);
            } else {
                return new DataTransfer("FAILURE", "User Could Not Be Modified");
            }
        }

        if (command.equals("UNBLOCKFRIEND")) {
            var values = (ArrayList<User>) data.getValue();
            User primary = values.get(0);
            User friend = values.get(1);

            primary.unBlock(friend);
            User prev = database.findUser(primary.getUserName());
            boolean success = database.modifyUser(primary, prev);

            if (success) {
                return new DataTransfer("SUCCESS", primary);
            } else {
                return new DataTransfer("FAILURE", "User Could Not Be Modified");
            }
        }

        if (command.equals("SEARCH")) {
            String value = (String) data.getValue();

            User found = database.findUser(value);
            boolean success = found != null;

            if (success) {
                return new DataTransfer("SUCCESS", found);
            } else {
                return new DataTransfer("FAILURE", "User Could Not Be Modified");
            }
        }

        if (command.equals("GETFRIENDSFEED")) {
            User value = (User) data.getValue();
            User currentValues = database.findUser(value.getUserName());
            boolean success = currentValues != null;

            if (success) {
                System.out.println(currentValues.getUserName());
                System.out.println(currentValues.getFriends());
                System.out.println(currentValues.getPosts());

                ArrayList<Post> out = currentValues.getFriendsFeed();
                return new DataTransfer("SUCCESS", out);
            } else {
                return new DataTransfer("FAILURE", "User Could Not Be Modified");
            }
        }

        return new DataTransfer("FAILURE", "Command NOT Found!");
    }

    public DataTransfer processPost(String command, DataTransfer data) {
        if (command.equals("CREATEPOST")) {
            ArrayList<Object> values = (ArrayList<Object>) data.getValue();
            User user = (User) values.get(0);
            String title = (String) values.get(1);
            String description = (String) values.get(2);
            String date = (String) values.get(3);

            Post p = null;
            p = new Post(user, title, description, date);

            user.post(p);
            User prev = database.findUser(user.getUserName());
            boolean success = database.modifyUser(user, prev);

            System.out.println(user.getUserName());
            System.out.println(user.getPosts());

            if (success) {
                return new DataTransfer("SUCCESS", p);
            } else {
                return new DataTransfer("FAILURE", "Post cannot be made!");
            }
        }

        if (command.equals("DELETEPOST")) {
            Post post = (Post) data.getValue();
            User user = post.getUser();
            user.removePost(post);

            User prev = database.findUser(user.getUserName());
            boolean success = database.modifyUser(user, prev);

            if (success) {
                return new DataTransfer("SUCCESS", user);
            } else {
                return new DataTransfer("FAILURE", "Post does not exist!");
            }
        }


        if (command.equals("LIKEPOST")) {
            ArrayList<Object> values = (ArrayList<Object>) data.getValue();

            Post post = (Post) values.get(0);
            post.addLike((User) values.get(1));

            System.out.println(post.getLikes());


            User user = post.getUser();
            User prev = database.findUser(user.getUserName());
            boolean success = database.modifyUser(user, prev);

            if (success) {
                return new DataTransfer("SUCCESS", post);
            } else {
                return new DataTransfer("FAILURE", "Post Could Not Be Modified");
            }
        }

        if (command.equals("UNLIKEPOST")) {
            ArrayList<Object> values = (ArrayList<Object>) data.getValue();

            Post post = (Post) values.get(0);
            post.removeLike((User) values.get(1));

            User user = post.getUser();
            User prev = database.findUser(user.getUserName());
            boolean success = database.modifyUser(user, prev);

            if (success) {
                return new DataTransfer("SUCCESS", post);
            } else {
                return new DataTransfer("FAILURE", "Post Could Not Be Modified");
            }
        }

        if (command.equals("SHOW")) {
            Post post = (Post) data.getValue();
            post.show();

            User user = post.getUser();
            User prev = database.findUser(user.getUserName());
            boolean success = database.modifyUser(user, prev);

            if (success) {
                return new DataTransfer("SUCCESS", post);
            } else {
                return new DataTransfer("FAILURE", "Post Could Not Be Modified");
            }
        }

        if (command.equals("HIDE")) {
            Post post = (Post) data.getValue();
            post.hide();

            User user = post.getUser();
            User prev = database.findUser(user.getUserName());
            boolean success = database.modifyUser(user, prev);

            if (success) {
                return new DataTransfer("SUCCESS", post);
            } else {
                return new DataTransfer("FAILURE", "Post Could Not Be Modified");
            }
        }

        return new DataTransfer("FAILURE", "Command NOT Found!");
    }

    public DataTransfer processComment(String command, DataTransfer data) {
        if (command.equals("CREATECOMMENT")) {
            ArrayList<Object> values = (ArrayList<Object>) data.getValue();

            User user = (User) values.get(0);
            Post post = (Post) values.get(1);
            String message = (String) values.get(2);
            String date = (String) values.get(3);

            Comment theComment = new Comment(user, post, message, date);
            post.addComment(theComment);

            User prev = database.findUser(post.getUser().getUserName());
            boolean success = database.modifyUser(post.getUser(), prev);

            if (success) {
                return new DataTransfer("SUCCESS", theComment);
            } else {
                return new DataTransfer("FAILURE", "Comment could Not Be Created");
            }
        }

        if (command.equals("DELETECOMMENT")) {
            ArrayList<Object> values = (ArrayList<Object>) data.getValue();
            Comment comment = (Comment) values.get(0);
            User user = (User) values.get(1);

            Post post = comment.getPost();
            post.removeComment(comment);

            User prev = database.findUser(post.getUser().getUserName());
            boolean success = database.modifyUser(post.getUser(), prev);

            if (success) {
                return new DataTransfer("SUCCESS", comment);
            } else {
                return new DataTransfer("FAILURE", "Comment could Not Be Modified");
            }
        }

        if (command.equals("LIKECOMMENT")) {
            ArrayList<Object> values = (ArrayList<Object>) data.getValue();

            Comment theComment = (Comment) values.get(0);
            Post post = theComment.getPost();

            theComment.addLike((User) values.get(1));

            User user = post.getUser();
            User prev = database.findUser(user.getUserName());

            boolean success = database.modifyUser(user, prev);

            if (success) {
                return new DataTransfer("SUCCESS", theComment);
            } else {
                return new DataTransfer("FAILURE", "Post Could Not Be Modified");
            }
        }

        if (command.equals("DISLIKECOMMENT")) {
            ArrayList<Object> values = (ArrayList<Object>) data.getValue();

            Comment theComment = (Comment) values.get(0);
            Post post = theComment.getPost();

            theComment.removeLike((User) values.get(1));

            User user = post.getUser();
            User prev = database.findUser(user.getUserName());
            boolean success = database.modifyUser(user, prev);

            if (success) {
                return new DataTransfer("SUCCESS", theComment);
            } else {
                return new DataTransfer("FAILURE", "Post Could Not Be Modified");
            }
        }

        return new DataTransfer("FAILURE", "Command NOT Found!");
    }

    public DataTransfer processCommands(DataTransfer data) {
        String[] commands = data.getMessage().split(" ");
        String scope = commands[0];
        String command = commands[1];

        DataTransfer out = new DataTransfer("FAILURE", "Invalid command");

        switch (scope) {
            case "USER" -> out = processUser(command, data);
            case "POST" -> out = processPost(command, data);
            case "COMMENT" -> out = processComment(command, data);
        }

        return out;
    }

    @Override
    public void run() {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream());

            oos.writeObject(new DataTransfer("CONNECTED", null));
            oos.flush();

            while (true) {
                System.out.println("Waiting for command...");
                DataTransfer data = (DataTransfer) ois.readObject();
                System.out.println(data.getMessage());

                if (data.getMessage().equals("TERMINATED")) {
                    break;
                }

                DataTransfer out = null;
                try {
                    out = processCommands(data);
                } catch (Exception e) {
                    out = new DataTransfer("FAILURE", e.getMessage());
                }

                oos.writeObject(out);
                oos.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
