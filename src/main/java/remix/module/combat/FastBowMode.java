package remix.module.combat;

import remix.setting.SettingOption;

public enum FastBowMode implements SettingOption {
    NCP("NCP"),
    VANILLA("Vanilla"),
    FLAG("Flag");

    private String displayName;

    FastBowMode(String displayNameValue) {
        this.displayName = displayNameValue;
    }

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
