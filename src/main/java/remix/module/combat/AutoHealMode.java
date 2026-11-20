package remix.module.combat;

import remix.setting.SettingOption;

public enum AutoHealMode implements SettingOption {
    NCP("NCP"),
    LEGIT("Legit");

    private String displayName;

    // region String decoding support

    // endregion

    // region Static initialization and string tables

    // endregion

    public String getDisplayName() {
        return this.displayName;
    }

    // region String decoding support

    // endregion

    AutoHealMode(String displayNameValue) {
        this.displayName = displayNameValue;
    }

    // region String decoding support

    // endregion

}
