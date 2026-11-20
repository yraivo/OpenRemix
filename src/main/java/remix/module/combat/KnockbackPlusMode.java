package remix.module.combat;

import remix.setting.SettingOption;

public enum KnockbackPlusMode implements SettingOption {
    GENERIC("Generic"),
    W_TAP("W-Tap"),
    FAITHFUL("Faithful");

    private String displayName;

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    KnockbackPlusMode(String displayNameValue) {
        this.displayName = displayNameValue;
    }

    // region String decoding support

    // endregion

    public String getDisplayName() {
        return this.displayName;
    }

    // region Static initialization and string tables

    // endregion

}
