package remix.compat.vecmath;

public class MismatchedSizeException extends RuntimeException {
    public MismatchedSizeException(String text) {
        super(text);
    }

    public MismatchedSizeException() {}
}
