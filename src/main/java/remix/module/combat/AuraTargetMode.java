package remix.module.combat;

import remix.setting.SettingOption;

public enum AuraTargetMode implements SettingOption {
    SWITCH("Switch"),
    SINGLE("Single"),
    TICK("Tick"),
    MULTI("Multi"),
    PRIORITY("Priority");

    private String displayName;

    public String getDisplayName() {
        return this.displayName;
    }

    AuraTargetMode(String displayNameValue) {
        this.displayName = displayNameValue;
    }

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    // region Static initialization and string tables

    // endregion

}
