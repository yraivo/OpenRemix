package remix.module.movement;

import remix.setting.SettingOption;

public enum HighJumpMode implements SettingOption {
    HYPIXEL("Hypixel"),
    CUBECRAFT("Cubecraft"),
    MCC("MCC");

    private String displayName;

    // region String decoding support

    // endregion

    HighJumpMode(String displayNameValue) {
        this.displayName = displayNameValue;
    }

    // region String decoding support

    // endregion

    // region Static initialization and string tables

    // endregion

    // region String decoding support

    // endregion

    public String getDisplayName() {
        return this.displayName;
    }
}
