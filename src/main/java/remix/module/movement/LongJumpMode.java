package remix.module.movement;

import remix.setting.SettingOption;

public enum LongJumpMode implements SettingOption {
    NCP("NCP"),
    NCP2("NCP2"),
    CUBECRAFT("Cubecraft"),
    MINEPLEX("Mineplex"),
    MINEPLEX2("Mineplex2");

    private String displayName;

    // region Static initialization and string tables

    // endregion

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    public String getDisplayName() {
        return this.displayName;
    }

    // region String decoding support

    // endregion

    LongJumpMode(String displayNameValue) {
        this.displayName = displayNameValue;
    }
}
