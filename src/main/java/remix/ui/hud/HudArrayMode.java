package remix.ui.hud;

import remix.setting.SettingOption;

public enum HudArrayMode implements SettingOption {
    SIMPLE("Simple"),
    WHITE("White"),
    DARK("Dark"),
    FLAT("Flat"),
    TRANSPARENT("Transparent"),
    SIDEBAR("Sidebar");

    private String displayName;

    // region String decoding support

    // endregion

    // region Static initialization and string tables

    // endregion

    HudArrayMode(String displayNameValue) {
        this.displayName = displayNameValue;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

}
