package remix.module.combat;

import remix.setting.SettingOption;

public enum RegenMode implements SettingOption {
    VANILLA("Vanilla"),
    FLAG("Flag");

    private String displayName;

    public String getDisplayName() {
        return this.displayName;
    }

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    RegenMode(String displayNameValue) {
        this.displayName = displayNameValue;
    }

    // region String decoding support

    // endregion

    // region Static initialization and string tables

    // endregion

}
