package remix.module.movement;

import remix.setting.SettingOption;

public enum NoSlowDownMode implements SettingOption {
    VANILLA("Vanilla"),
    NCP("NCP"),
    HYPIXEL("Hypixel"),
    LEMON("Lemon");

    private String displayName;

    // region Static initialization and string tables

    // endregion

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    public String getDisplayName() {
        return this.displayName;
    }

    // region String decoding support

    // endregion

    NoSlowDownMode(String displayNameValue) {
        this.displayName = displayNameValue;
    }
}
