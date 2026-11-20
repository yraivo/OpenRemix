package remix.module.render;

import remix.setting.SettingOption;

public enum StorageESPMode implements SettingOption {
    SOLID("Solid"),
    LINES("Lines");

    private String displayName;

    // region Static initialization and string tables

    // endregion

    public String getDisplayName() {
        return this.displayName;
    }

    // region String decoding support

    // endregion

    StorageESPMode(String displayNameValue) {
        this.displayName = displayNameValue;
    }

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

}
