package remix.client.friend;

public class FriendEntry {
    private static String c;

    private String alias;
    private String name;

    public String getName() {
        return this.name;
    }

    public FriendEntry(String text, String textValue) {
        this.name = text;
        this.alias = textValue;
    }

    // region String decoding support

    // endregion

    public FriendEntry(String text) {
        this(text, c);
    }

    // region String decoding support

    // endregion

    public String getAlias() {
        return this.alias;
    }

    static {
        c = "Friend";
    }
}
