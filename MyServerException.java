public class MyServerException extends RuntimeException {
    public MyServerException(String message) {
        super(message);
    }

    public MyServerException(String message, Throwable couse) {
        super(message, couse);
    }
}
