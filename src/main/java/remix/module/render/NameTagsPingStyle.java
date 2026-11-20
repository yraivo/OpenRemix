package remix.module.render;

import remix.setting.SettingOption;

public enum NameTagsPingStyle implements SettingOption {
    NUMERIC("Numeric"),
    ICON("Icon");

    private String displayName;

    // region String decoding support

    // endregion

    NameTagsPingStyle(String displayNameValue) {
        this.displayName = displayNameValue;
    }

    // region Static initialization and string tables

    // endregion

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    public String getDisplayName() {
        return this.displayName;
    }
}
