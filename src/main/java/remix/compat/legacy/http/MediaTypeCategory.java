package remix.compat.legacy.http;

public enum MediaTypeCategory {
    APPLICATION(new String("application")),
    AUDIO(new String("audio")),
    EXAMPLE(new String("example")),
    IMAGE(new String("image")),
    MESSAGE(new String("message")),
    MODEL(new String("model")),
    MULTIPART(new String("multipart")),
    TEXT(new String("text")),
    VIDEO(new String("video"));

    private String mimePrefix;


    MediaTypeCategory(String text) {
        this.mimePrefix = text;
    }

    // region String decoding support

    // endregion

    @Override
    public String toString() {
        return this.getMimePrefix();
    }

    public String getMimePrefix() {
        return this.mimePrefix;
    }

    // region String decoding support

    // endregion

}
