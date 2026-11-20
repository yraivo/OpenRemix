package remix.setting;

public enum SettingType {
    BOOLEAN("Check"),
    NUMERIC("Number"),
    ENUM("Selection");

    public String id;

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    // region Static initialization and string tables

    // endregion

    // region String decoding support

    // endregion

    SettingType(String text) {
        this.id = text;
    }
}
