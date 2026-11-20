package remix.module.movement;

import remix.setting.SettingOption;

public enum JesusMode implements SettingOption {
    NORMAL("Normal"),
    NCP("NCP"),
    DOLPHIN("Dolphin");

    private String displayName;

    public String getDisplayName() {
        return this.displayName;
    }

    // region Static initialization and string tables

    // endregion

    JesusMode(String displayNameValue) {
        this.displayName = displayNameValue;
    }

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

}
