public class DataTransfer {
    private String message;
    private Object value;

    public DataTransfer(String message, Object value) {
        this.message = message;
        this.value = value;
    }


    public String getMessage() {
        return message;
    }

    public Object getValue() {
        return value;
    }
}
