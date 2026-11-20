package remix.compat.legacy.codec;

public final class CodecParseException extends CodecStageException {
    private static final long serialVersionUID = 5981236598016L;

    public CodecParseException() {}

    public CodecParseException(String text, Throwable throwable) {
        super(text, throwable);
    }

    public CodecParseException(String text) {
        super(text);
    }

    public CodecParseException(Throwable throwable) {
        super(throwable);
    }
}
