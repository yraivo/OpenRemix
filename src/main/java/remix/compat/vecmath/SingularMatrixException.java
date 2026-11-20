package remix.compat.vecmath;

public class SingularMatrixException extends RuntimeException {
    public SingularMatrixException(String text) {
        super(text);
    }

    public SingularMatrixException() {}
}
