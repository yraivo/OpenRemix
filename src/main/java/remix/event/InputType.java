package remix.event;

public enum InputType {
    KEY_PRESS("Keyboard", 1),
    LEFT_CLICK("Mouse", 2),
    MIDDLE_CLICK("Mouse", 3),
    RIGHT_CLICK("Mouse", 4);

    private int defaultKeyCode;

    private static String[] legacyInputTypeMarker;
    private String displayName;

    public int getDefaultKeyCode() {
        return this.defaultKeyCode;
    }

    // region String decoding support

    // endregion

    public static String[] getLegacyInputTypeMarker() {
        return remix.event.InputType.legacyInputTypeMarker;
    }

    InputType(String text, int j) {
        this.defaultKeyCode = j;
        this.displayName = text;
    }

    // region String decoding support

    // endregion

    public static void setLegacyInputTypeMarker(String[] astring) {
        remix.event.InputType.legacyInputTypeMarker = astring;
    }

    // region String decoding support

    // endregion

    public String getDisplayName() {
        return this.displayName;
    }

    // region Static initialization and string tables

    // endregion

}
