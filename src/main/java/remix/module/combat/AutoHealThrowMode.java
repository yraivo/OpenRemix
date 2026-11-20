package remix.module.combat;

import remix.setting.SettingOption;

public enum AutoHealThrowMode implements SettingOption {
    DOWN("Down"),
    JUMP("Jump");

    private String displayName;

    // region String decoding support

    // endregion

    public String getDisplayName() {
        return this.displayName;
    }

    // region String decoding support

    // endregion

    AutoHealThrowMode(String displayNameValue) {
        this.displayName = displayNameValue;
    }

    // region String decoding support

    // endregion

    // region Static initialization and string tables

    // endregion

}
