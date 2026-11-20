package remix.module.combat;

import remix.setting.SettingOption;

public enum VelocityMode implements SettingOption {
    NORMAL("Normal"),
    AAC("AAC"),
    FLAG("Flag");

    private String displayName;

    public String getDisplayName() {
        return this.displayName;
    }

    VelocityMode(String displayNameValue) {
        this.displayName = displayNameValue;
    }

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    // region Static initialization and string tables

    // endregion

    // region String decoding support

    // endregion

}
