package remix.ui.hud;

import remix.setting.SettingOption;

public enum ChamsColor implements SettingOption {
    CLIENT("Client"),
    TEAM("Team");

    private String displayName;

    // region Static initialization and string tables

    // endregion

    public String getDisplayName() {
        return this.displayName;
    }

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    ChamsColor(String displayNameValue) {
        this.displayName = displayNameValue;
    }

    // region String decoding support

    // endregion

}
