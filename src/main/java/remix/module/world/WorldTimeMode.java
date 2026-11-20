package remix.module.world;

import remix.setting.SettingOption;

public enum WorldTimeMode implements SettingOption {
    CUSTOM("Custom"),
    REAL("Real");

    private String displayName;

    WorldTimeMode(String displayNameValue) {
        this.displayName = displayNameValue;
    }

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    public String getDisplayName() {
        return this.displayName;
    }

    // region Static initialization and string tables

    // endregion

    // region String decoding support

    // endregion

}
