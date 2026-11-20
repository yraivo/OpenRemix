package remix.compat.legacy.codec;

public class CodecStageException extends Exception {
    private static final long serialVersionUID = 39856293132125L;

    public CodecStageException() {}

    public CodecStageException(String text) {
        super(text);
    }

    public CodecStageException(Throwable throwable) {
        super(throwable);
    }

    public CodecStageException(String text, Throwable throwable) {
        super(text, throwable);
    }
}
