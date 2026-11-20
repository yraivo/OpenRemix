package remix.module.movement;

import remix.setting.SettingOption;

public enum NoFallSetBackMode implements SettingOption {
    LASTPOS("LastPos"),
    FLAG("Flag");

    private String displayName;

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    public String getDisplayName() {
        return this.displayName;
    }

    // region Static initialization and string tables

    // endregion

    NoFallSetBackMode(String displayNameValue) {
        this.displayName = displayNameValue;
    }
}
