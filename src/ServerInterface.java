public interface ServerInterface {
    DataTransfer processUser(String command, DataTransfer data);
    DataTransfer processPost(String command, DataTransfer data);
    DataTransfer processComment(String command, DataTransfer data);
    DataTransfer processCommands(DataTransfer data);
}
