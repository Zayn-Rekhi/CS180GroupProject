public class DataTransfer {
    private int status;
    private String message;
    private Object value;

    public DataTransfer(int status, String message, Object value) {
        this.status = status;
        this.message = message;
        this.value = value;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public Object getValue() {
        return value;
    }
}
