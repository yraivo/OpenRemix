package remix.module.combat;

import remix.setting.SettingOption;

public enum CriticalsDelayMode implements SettingOption {
    CUSTOM("Custom"),
    AFTER("After");

    private String displayName;

    // region String decoding support

    // endregion

    CriticalsDelayMode(String displayNameValue) {
        this.displayName = displayNameValue;
    }

    // region String decoding support

    // endregion

    // region Static initialization and string tables

    // endregion

    public String getDisplayName() {
        return this.displayName;
    }

    // region String decoding support

    // endregion

}
