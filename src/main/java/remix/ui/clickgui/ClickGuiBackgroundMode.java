package remix.ui.clickgui;

import remix.setting.SettingOption;

public enum ClickGuiBackgroundMode implements SettingOption {
    COLORFUL("Colorful"),
    DARK("Dark"),
    SIMPLE("Simple");

    private String displayName;

    // region String decoding support

    // endregion

    public String getDisplayName() {
        return this.displayName;
    }

    // region String decoding support

    // endregion

    ClickGuiBackgroundMode(String displayNameValue) {
        this.displayName = displayNameValue;
    }

    // region String decoding support

    // endregion

    // region Static initialization and string tables

    // endregion

}
