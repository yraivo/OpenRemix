package remix.module.player;

import remix.setting.SettingOption;

public enum InvManagerMode implements SettingOption {
    VANILLA("Vanilla"),
    OPENINV("OpenInventory");

    private String displayName;

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    InvManagerMode(String displayNameValue) {
        this.displayName = displayNameValue;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    // region Static initialization and string tables

    // endregion

    // region String decoding support

    // endregion

}
