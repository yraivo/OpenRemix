package remix.module.player;

import remix.setting.SettingOption;

public enum FastUseMode implements SettingOption {
    NCP("NCP"),
    FAITHFUL("Faithful");

    private String displayName;

    FastUseMode(String displayNameValue) {
        this.displayName = displayNameValue;
    }

    // region String decoding support

    // endregion

    public String getDisplayName() {
        return this.displayName;
    }

    // region String decoding support

    // endregion

    // region Static initialization and string tables

    // endregion

    // region String decoding support

    // endregion

}
