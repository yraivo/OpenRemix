package remix.module.player;

import remix.setting.SettingOption;

public enum DerpMode implements SettingOption {
    RANDOM("Random"),
    ROTATE("Rotate");

    private String displayName;

    public String getDisplayName() {
        return this.displayName;
    }

    DerpMode(String displayNameValue) {
        this.displayName = displayNameValue;
    }

    // region Static initialization and string tables

    // endregion

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

}
