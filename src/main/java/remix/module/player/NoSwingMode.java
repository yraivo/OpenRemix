package remix.module.player;

import remix.setting.SettingOption;

public enum NoSwingMode implements SettingOption {
    VANILLA("Vanilla"),
    NCP("NCP");

    private String displayName;

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    NoSwingMode(String displayNameValue) {
        this.displayName = displayNameValue;
    }

    // region String decoding support

    // endregion

    // region Static initialization and string tables

    // endregion

    public String getDisplayName() {
        return this.displayName;
    }
}
